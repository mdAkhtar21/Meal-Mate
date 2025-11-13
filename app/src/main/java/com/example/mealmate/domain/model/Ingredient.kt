package com.example.mealmate.domain.model

data class Ingredient(
    val ingredientId: Long = 0L,
    val tempKey: Long? = null,
    val recipeId: Long? = null,
    val name: String,
    val category: String,
    val measurement: String
)