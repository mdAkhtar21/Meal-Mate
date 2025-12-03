package com.example.mealmate.domain.model

data class RecipeSource(
    val sourceId:Long,
    val tempKey: Long? = null,
    val recipeId: Long? = null,
    val name:String,
    val url:String
)