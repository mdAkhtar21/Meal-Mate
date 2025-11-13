package com.example.mealmate.data.local.AddRecipe


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "recipes")
@TypeConverters(Converters::class)
data class RecipeTableEntity(
    @PrimaryKey(autoGenerate = true)
    val recipeId: Long = 0L,
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
