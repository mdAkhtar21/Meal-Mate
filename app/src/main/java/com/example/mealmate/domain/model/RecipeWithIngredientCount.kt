package com.example.mealmate.domain.model


data class RecipeWithIngredientCount(
    val recipe: RecipeTable,
    val ingredientCount: Int
)