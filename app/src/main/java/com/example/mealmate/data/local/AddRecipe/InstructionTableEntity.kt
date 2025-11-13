package com.example.mealmate.data.local.AddRecipe

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "instructions",
    foreignKeys = [
        ForeignKey(
            entity = RecipeTableEntity::class,
            parentColumns = ["recipeId"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class InstructionTableEntity(
    @PrimaryKey(autoGenerate = true)
    val instructionId: Long = 0L,
    val tempKey: Long? = null,  // ✅ make nullable
    var recipeId: Long? = null,
    val step: Int,
    val description: String
)
