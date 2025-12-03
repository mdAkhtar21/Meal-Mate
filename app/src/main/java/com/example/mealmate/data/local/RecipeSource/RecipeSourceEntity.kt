package com.example.mealmate.data.local.RecipeSource

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity

@Entity(
    tableName = "recipe_sources",
    foreignKeys = [
        ForeignKey(
            entity = RecipeTableEntity::class,
            parentColumns = ["recipeId"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class RecipeSourceEntity(
    @PrimaryKey(autoGenerate = true)
    val sourceId: Long = 0L,
    val tempKey: Long? = null,
    var recipeId: Long? = null,
    val name: String,
    val url: String
)