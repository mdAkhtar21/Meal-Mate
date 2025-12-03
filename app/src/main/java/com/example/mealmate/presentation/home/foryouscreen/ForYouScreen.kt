package com.example.mealmate.presentation.home.foryouscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealmate.data.local.LocalData.SampleRecipes
import com.example.mealmate.navigation.Screen

@Composable
fun ForYouScreen(
    navController: NavController,
    viewModel: YouRecipeViewModel= hiltViewModel()
) {
    val context = LocalContext.current
    val recipeList = viewModel.recipes.collectAsState().value
    val userId=viewModel.userId.collectAsState().value

    LaunchedEffect(Unit) {
        if(StaticRecipeStore.staticRecipes.isEmpty()){
            val recipes = SampleRecipes.getForYouRecipes(context,userId)
            StaticRecipeStore.staticRecipes.addAll(recipes)
        }
        viewModel.loadRecipes()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(top = 10.dp)
    ) {
        LazyColumn {
            items(recipeList) { item ->

                RecipeItems(
                    image = item.recipe.recipeImage,
                    name = item.recipe.title,
                    count = item.ingredientCount,
                    recipeId = item.recipe.recipeId,
                    onClick = { id ->
                        navController.navigate(Screen.DetailScreen.passId(id))
                    }
                )
            }
        }
    }
}