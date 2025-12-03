package com.example.mealmate.data.Mapper

import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity
import com.example.mealmate.data.local.Ingredient.IngredientTableEntity
import com.example.mealmate.data.local.Instruction.InstructionTableEntity
import com.example.mealmate.data.local.RecipeSource.RecipeSourceEntity
import com.example.mealmate.domain.model.Ingredient
import com.example.mealmate.domain.model.Instruction
import com.example.mealmate.domain.model.RecipeSource
import com.example.mealmate.domain.model.RecipeTable

fun RecipeTableEntity.toDomain(): RecipeTable {
    return RecipeTable(
        recipeId = this.recipeId,
        userId = this.userId,
        title = this.title,
        description = this.description,
        recipeImage = this.recipeImage,
        category = this.category,
        style = this.style,
        mealType = this.mealType,
        serving = this.serving,
        preprationTime = this.preprationTime,
        cookingTime = this.cookingTime
    )
}

// --- Ingredient ---
fun IngredientTableEntity.toDomain() = Ingredient(
    ingredientId = this.ingredientId,
    tempKey = this.tempKey,
    recipeId = this.recipeId,
    name = this.name,
    category = this.category,
    measurement = this.measurement
)

// --- Instruction ---
fun InstructionTableEntity.toDomain() = Instruction(
    instructionId = this.instructionId,
    tempKey = this.tempKey,
    recipeId = this.recipeId,
    step = this.step,
    value = this.description
)

// --- Recipe Source ---
fun RecipeSourceEntity.toDomain() = RecipeSource(
    sourceId = this.sourceId,
    tempKey = this.tempKey,
    recipeId = this.recipeId,
    name = this.name,
    url = this.url
)