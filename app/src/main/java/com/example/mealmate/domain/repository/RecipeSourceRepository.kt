package com.example.mealmate.domain.repository

import com.example.mealmate.domain.model.RecipeSource

interface RecipeSourceRepository {
    suspend fun addSource(source: RecipeSource)

    suspend fun deleteSource(tempKey: Long?, sourceId: Long?)

    suspend fun getSourcesForRecipe(recipeId: Long): List<RecipeSource>

    suspend fun getSourcesForTempKey(tempKey: Long): List<RecipeSource>
}