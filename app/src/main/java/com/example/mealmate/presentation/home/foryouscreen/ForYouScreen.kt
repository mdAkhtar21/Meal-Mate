package com.example.mealmate.presentation.home.foryouscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mealmate.R


@Composable
fun ForYouScreen(navController: NavController) {
    val recipes = listOf(
        RecipeData(R.drawable.maggies, "Maggie", "2 September"),
        RecipeData(R.drawable.maggies, "Maggie", "2 September"),
        RecipeData(R.drawable.maggies, "Maggie", "2 September"),
        RecipeData(R.drawable.maggies, "Maggie", "2 September"),
        RecipeData(R.drawable.maggies, "Maggie", "2 September"),
        RecipeData(R.drawable.maggies, "Maggie", "2 September"),
        RecipeData(R.drawable.maggies, "Maggie", "2 September"),
        RecipeData(R.drawable.maggies, "Maggie", "2 September"),
        RecipeData(R.drawable.maggies, "Maggie", "2 September"),
        RecipeData(R.drawable.maggies, "Maggie", "2 September")
    )

    Column(modifier = Modifier.fillMaxSize().padding(top=10.dp)) {
        LazyColumn {
            items(recipes) { recipe ->
                RecipeItems(
                    image =recipe.image,
                    name = recipe.name,
                    date = recipe.date
                )
            }
        }
    }
}
