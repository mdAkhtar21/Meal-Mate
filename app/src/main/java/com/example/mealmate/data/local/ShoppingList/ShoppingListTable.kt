package com.example.mealmate.data.local.shoppinglist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list")
data class ShoppingListTable(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recipeId: Long,
    val userId: Long,
    val ingredientName: String,
    val categoryName:String,
    val quantity: String,
    val comment: String,
    val isChecked: Boolean = false
)
