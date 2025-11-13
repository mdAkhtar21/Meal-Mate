package com.example.mealmate.domain.repository

import com.example.mealmate.data.local.AddRecipe.IngredientTableEntity
import com.example.mealmate.data.local.AddRecipe.InstructionTableEntity
import com.example.mealmate.data.local.AddRecipe.RecipeSource
import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity


interface RecipeRepository {

    // Insert a full recipe with ingredients, instructions, and sources
    suspend fun insertFullRecipe(
        recipe: RecipeTableEntity,
        // 🌟 FIX: Simplified signature to only require the recipe and tempKey
        tempKey: Long
    ): Long

    // Fetch all recipes
    suspend fun getAllRecipes(): List<RecipeTableEntity>

    // Fetch recipe by ID
    suspend fun getRecipeById(id: Long): RecipeTableEntity?

    // Delete a recipe
    suspend fun deleteRecipe(recipe: RecipeTableEntity)

    // Required methods for FullRecipeDetail
    suspend fun getIngredientsForRecipe(recipeId: Long): List<IngredientTableEntity>
    suspend fun getInstructionsForRecipe(recipeId: Long): List<InstructionTableEntity>
    suspend fun getSourcesForRecipe(recipeId: Long): List<RecipeSource>

}
