package com.example.mealmate.domain.repository

import com.example.mealmate.data.local.AddRecipe.RecipeSource

interface RecipeSourceRepository {
    // Add a source (pre-recipe or post-recipe)
    suspend fun addSource(source: RecipeSource)

    // Delete a source by tempKey (pre-recipe) or by sourceId (post-recipe)
    suspend fun deleteSource(tempKey: Long?, sourceId: Long?)

    // Get sources for a specific recipe (post-recipe)
    suspend fun getSourcesForRecipe(recipeId: Long): List<RecipeSource>

    // Get sources for a pre-recipe using tempKey
    suspend fun getSourcesForTempKey(tempKey: Long): List<RecipeSource>
}