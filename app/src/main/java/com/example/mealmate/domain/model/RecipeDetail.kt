package com.example.mealmate.domain.model

data class RecipeDetail(
    val recipe: RecipeTable,
    val ingredients: List<Ingredient>,
    val instructions: List<Instruction>,
    val sources: List<RecipeSource>
)