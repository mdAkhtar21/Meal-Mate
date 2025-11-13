package com.example.mealmate.domain.model

data class MealPlan(
    val id: Long = 0,
    val recipeId: Long,
    val mealType: String,
    val day: String
)
