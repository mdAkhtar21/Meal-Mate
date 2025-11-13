package com.example.mealmate.presentation.plan

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.mealmate.presentation.common.AppBar
import com.example.mealmate.presentation.common.BottomBar
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mealmate.navigation.Screen
import com.example.mealmate.presentation.common.SwipePlan
import com.example.mealmate.presentation.home.addrecipie.ingredient.IngredientViewModel
import com.example.mealmate.presentation.list.ShoppingListBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanScreen(
    navController: NavController,
    viewModel: PlanScreenViewModel= hiltViewModel()
) {
    // Load Data ✅
    LaunchedEffect(Unit) {
        viewModel.showList()
    }

    var selectedBottomItem by remember { mutableStateOf(1) }
    val planRecipes = viewModel.mealPlans.collectAsState().value

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val showBottomSheet = remember { mutableStateOf(false) } // ✅ Use this to toggle sheet
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
                            onClick = {},
                            modifier = Modifier
                                .height(40.dp),
                            shape = RoundedCornerShape(
                                topStart = 20.dp,
                                bottomStart = 20.dp,
                                topEnd = 0.dp,
                                bottomEnd = 0.dp
                            ),
                            contentPadding = PaddingValues(start = 12.dp, end = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Add to shopping list",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White,
                                fontWeight = FontWeight.W500
                            )
                        }
                        Spacer(modifier = Modifier.width(2.dp))
                        Button(
                            onClick = {
                                showBottomSheet.value = true
                            },
                            modifier = Modifier
                                .height(40.dp).width(40.dp),
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
                Text("No Meal Plans yet", fontSize = 18.sp, color = Color.Gray)
            }
            return@Scaffold
        }
        val grouped = planRecipes.groupBy { it.mealPlan.day }
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
                    SwipePlan(
                        item = item,
                        onClick = { id ->
                            navController.navigate(Screen.DetailScreen.passId(id))
                        },
                        onDelete={id->
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
}
