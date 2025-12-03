package com.example.mealmate.data.local.Ingredient

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity

@Entity(
    tableName = "ingredients",
    foreignKeys = [
        ForeignKey(
            entity = RecipeTableEntity::class,
            parentColumns = ["recipeId"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class IngredientTableEntity(
    @PrimaryKey(autoGenerate = true)
    val ingredientId: Long = 0L,
    var recipeId: Long? = null, // nullable until recipe is created
    val tempKey: Long? = null,
    val name: String,
    val category: String,
    val measurement: String
)