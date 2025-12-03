package com.example.mealmate.domain.model

import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity

data class RecipeTable(
    val recipeId: Long,
    val userId:Long,
    val title: String,
    val description: String,
    val recipeImage: ByteArray?,
    val category: String,
    val style: String,
    val mealType: List<String>,
    val serving: Int,
    val preprationTime: String,
    val cookingTime: String
)


