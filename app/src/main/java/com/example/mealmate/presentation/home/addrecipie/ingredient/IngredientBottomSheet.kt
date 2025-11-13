@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mealmate.presentation.home.addrecipie.ingredient

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mealmate.presentation.common.SwipeIngredientItem
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientBottomSheet(
    tempKey:Long,
    sheetState: SheetState,
    scope: CoroutineScope,
    showIngredientSheet: MutableState<Boolean>,
) {
    val ingredientViewModel: IngredientViewModel = hiltViewModel()
    val ingredients by ingredientViewModel.ingredient.collectAsState()
    val showAddIngredientSheet = remember { mutableStateOf(false) }

    LaunchedEffect(showIngredientSheet.value) {
        if (showIngredientSheet.value) ingredientViewModel.loadIngredients(tempKey)
    }


    if (showIngredientSheet.value && !showAddIngredientSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showIngredientSheet.value = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Ingredient",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (ingredients.isEmpty()) {
                    Text(
                        text = "There are no ingredients yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }else{
                    Text(
                        text = "There are ${ingredients.size} ingredients",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Button to open Add Ingredient sheet
                Button(
                    onClick = {
                        showIngredientSheet.value = false
                        showAddIngredientSheet.value = true
                    },
                    modifier = Modifier.width(120.dp)
                ) {
                    Row {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Add", style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Display added ingredients
                if (ingredients.isNotEmpty()) {
                    val groupedIngredients = ingredients.groupBy { it.category }

                    LazyColumn {
                        groupedIngredients.forEach { (category, items) ->
                            item {
                                Text(
                                    text = category,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(vertical = 6.dp)
                                )
                            }

                            items(items, key = { it.ingredientId }) { item ->
                                SwipeIngredientItem(
                                    ingredient = item,
                                    viewModel = ingredientViewModel,
                                    recipeId = tempKey,
                                    showCategory = false
                                )
                            }
                        }
                    }
                }

            }
        }
    }

    if (showAddIngredientSheet.value) {
        IngredientBottomSheet2(
            tempKey = tempKey,
            sheetState = sheetState,
            scope = scope,
            showBottomSheet = showAddIngredientSheet,
            showNextSheet = showIngredientSheet
        )
    }
}
