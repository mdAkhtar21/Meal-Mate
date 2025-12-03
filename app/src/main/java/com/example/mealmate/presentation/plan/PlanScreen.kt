package com.example.mealmate.presentation.plan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import com.example.mealmate.R
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealmate.domain.model.RecipeTable
import com.example.mealmate.navigation.Screen
import com.example.mealmate.presentation.common.AppBar
import com.example.mealmate.presentation.common.BottomBar
import com.example.mealmate.presentation.common.SwipePlan
import com.example.mealmate.presentation.home.foryouscreen.StaticRecipeStore
import com.example.mealmate.presentation.list.shoppinglist.ShoppingListBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanScreen(
    navController: NavController,
    viewModel: PlanScreenViewModel= hiltViewModel()
) {
    // Load Data ✅
    LaunchedEffect(Unit) {
        viewModel.loadMealPlans()
    }
    var showDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedBottomItem by remember { mutableStateOf(1) }
    val planRecipes = viewModel.mealPlans.collectAsState().value

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val showBottomSheet = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppBar(
                topBarHeader = "Plan",
                onBackClick = {
                    val popped = navController.popBackStack()
                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { showBottomSheet.value = true },
                            modifier = Modifier.height(40.dp),
                            shape = RoundedCornerShape(
                                topStart = 20.dp,
                                bottomStart = 20.dp,
                                topEnd = 0.dp,
                                bottomEnd = 0.dp
                            ),
                            contentPadding = PaddingValues(start = 12.dp, end = 5.dp)
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Add to shopping list",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White,
                                fontWeight = FontWeight.W500
                            )
                        }

                        Spacer(modifier = Modifier.width(2.dp))

                        Box {
                            Button(
                                onClick = { expanded = true },
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp),
                                shape = RoundedCornerShape(
                                    topStart = 0.dp,
                                    bottomStart = 0.dp,
                                    topEnd = 20.dp,
                                    bottomEnd = 20.dp
                                ),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text(text = "Click plan") },
                                    onClick = {
                                        showDialog = true
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                }
            )
        },
        bottomBar = {
            BottomBar(
                selectedBottomItem = selectedBottomItem,
                onItemSelected = { selectedBottomItem = it },
                navController = navController
            )
        },
    ) { innerPadding ->

        if (planRecipes.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.file),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(80.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "Empty Meal plan",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        "Add a new Entry and it will show up here",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black.copy(alpha = 0.9f),
                    )
                }
            }

            return@Scaffold
        }
        val grouped = planRecipes

        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            grouped.forEach { (day, itemsForDay) ->
                item {
                    Text(
                        text = day,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp)
                    )
                }

                items(itemsForDay, key = { it.mealPlan.id }) { item ->
                    val recipeId = item.mealPlan.recipeId

                    val staticData = StaticRecipeStore.staticRecipes
                        .find { it.recipe.recipeId == recipeId }
                    val resolvedRecipe: RecipeTable? =
                        item.recipe ?: staticData?.recipe


                    SwipePlan(
                        item = item.copy(recipe = resolvedRecipe),
                        onClick = { id ->
                            navController.navigate(Screen.DetailScreen.passId(id))
                        },
                        onDelete = { id ->
                            viewModel.deleteMealPlan(id)
                        },
                        showDays = false
                    )
                }
            }
        }

        // ✅ Place BottomSheet OUTSIDE Scaffold content
        if (showBottomSheet.value) {
            ShoppingListBottomSheet(
                sheetState = sheetState,
                showBottomSheet = showBottomSheet,
                scope = scope
            )
        }
    }



    if (showDialog) {
        SimpleAlertDialog(showDialogState = remember { mutableStateOf(showDialog) }) {confirmed->
            showDialog = false
            if (confirmed) {
                viewModel.clearAllMealPlan()
            }
        }
    }
}


@Composable
fun SimpleAlertDialog(
    showDialogState: MutableState<Boolean>,
    onDismiss: (Boolean) -> Unit,
) {
    if (showDialogState.value) {
        AlertDialog(
            onDismissRequest = {
                showDialogState.value = false
                onDismiss(false)
            },
            title = { Text(text = "Confirm to Delete") },
            text = { Text("Are you sure you want to clear your meal plan? This process cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    showDialogState.value = false
                    onDismiss(true)
                }) {
                    Text("Ok")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialogState.value = false
                    onDismiss(false)
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}