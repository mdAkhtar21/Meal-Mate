package com.example.mealmate.domain.model


data class ShoppingListItem(
    val id: Long = 0L,
    val recipeId: Long,
    val userId:Long,
    val ingredientName: String,
    val categoryName: String,
    val quantity: String,
    val comment: String? = null,
    val isChecked: Boolean = false
)
