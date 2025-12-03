package com.example.mealmate.presentation.home.myrecipies

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealmate.navigation.Screen
import com.example.mealmate.presentation.common.SwipeRecipe

@Composable
fun MyRecipie(
    navController: NavController,
    viewModel: MyRecipeViewModel = hiltViewModel()
) {
    val recipeState by viewModel.recipes.collectAsState()

    // Load only once when screen opens
    LaunchedEffect(Unit) {
        viewModel.loadRecipes()
    }
    LazyColumn {
        items(recipeState) { item ->
            SwipeRecipe(item = item, onClick = { id ->
                navController.navigate(Screen.DetailScreen.passId(id))
            },
                onDelete = {id->
                    viewModel.deleteRecipe(item.recipe)
                })
        }
    }
}
