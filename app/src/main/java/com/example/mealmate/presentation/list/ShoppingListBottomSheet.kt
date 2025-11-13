package com.example.mealmate.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mealmate.domain.model.ShoppingListItem
import com.example.mealmate.presentation.plan.PlanScreenViewModel
import kotlinx.coroutines.CoroutineScope


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListBottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    showBottomSheet: MutableState<Boolean>,
    planViewModel: PlanScreenViewModel = hiltViewModel(),
    shoppingViewModel: ShoppingListViewModel = hiltViewModel(),
    listviewModel:ListViewModel= hiltViewModel()
) {
    val planList by planViewModel.mealPlans.collectAsState()
    val ingredients by shoppingViewModel.ingredients.collectAsState()
    val shoppingList by listviewModel.shoppingListItems.collectAsState()


    LaunchedEffect(planList) {
        val recipeIds = planList.map { it.mealPlan.recipeId }
        shoppingViewModel.loadIngredientsForRecipes(recipeIds)
    }

    val ingredientsForPlan = planList
        .flatMap { plan -> ingredients.filter { it.recipeId == plan.mealPlan.recipeId } }
        .distinctBy { it.ingredientId }


    val checkedIngredients = remember {
        androidx.compose.runtime.mutableStateMapOf<Long, Boolean>()
    }

    LaunchedEffect(ingredientsForPlan) {
        ingredientsForPlan.forEach { ing ->
            if (!checkedIngredients.containsKey(ing.ingredientId)) {
                checkedIngredients[ing.ingredientId] = false
            }
        }
    }

    val ingredientsByCategory = ingredientsForPlan.groupBy { it.category }

    ModalBottomSheet(
        onDismissRequest = { showBottomSheet.value = false },
        sheetState = sheetState
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Add to Shopping List",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyColumn {
                ingredientsByCategory.forEach { (category, ingredientsInCategory) ->
                    item {
                        Text(
                            text = category,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    }

                    items(ingredientsInCategory) { ingredient ->
                        ShowPlanCheckBox(
                            name = ingredient.name,
                            checked = checkedIngredients[ingredient.ingredientId] ?: false,
                            onCheckedChange = { checked ->
                                checkedIngredients[ingredient.ingredientId] = checked
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    val selectedIngredients = ingredientsForPlan.filter {
                        checkedIngredients[it.ingredientId] == true
                    }

                    selectedIngredients.forEach { ing ->

                        val alreadyExists=shoppingList.any{it.ingredientName.equals(ing.name, ignoreCase = true)}
                        if(!alreadyExists){
                            listviewModel.insertShoppingListItem(
                                ShoppingListItem(
                                    id = 0,
                                    recipeId = ing.recipeId ?: 0L,
                                    ingredientName = ing.name,
                                    categoryName = ing.category,
                                    quantity = "",
                                    comment = ""
                                )
                            )
                        }
                    }
                    showBottomSheet.value=false

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                ),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
                    .height(45.dp)
            ) {
                Text(
                    text = "Add Ingredient",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 18.sp
                )
            }


        }
    }
}





@Composable
fun ShowPlanCheckBox(
    name: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            color = Color.Black,
            fontWeight = FontWeight.W600,
            style = MaterialTheme.typography.bodyMedium
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
