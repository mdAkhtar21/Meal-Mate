package com.example.mealmate.domain.repository

import com.example.mealmate.domain.model.Ingredient

interface IngredientRepository {
    // Add ingredient (pre or post recipe)
    suspend fun addIngredient(ingredient: Ingredient)

    // Delete ingredient by tempKey (pre-recipe) or recipeId (post-recipe)
    suspend fun deleteIngredient(tempKey: Long?, ingredientId: Long?)

    // Get ingredients for a specific recipe (post-recipe)
    suspend fun getIngredientsForRecipe(recipeId: Long): List<Ingredient>

    // Get ingredients for a pre-recipe using tempKey
    suspend fun getIngredientsForTempKey(tempKey: Long): List<Ingredient>
}
