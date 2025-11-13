package com.example.mealmate.data.repository

import com.example.mealmate.data.local.AddRecipe.RecipeSource
import com.example.mealmate.data.local.AddRecipe.RecipeSourceDao
import com.example.mealmate.domain.repository.RecipeSourceRepository
import javax.inject.Inject

class RecipeSourceRepositoryImpl @Inject constructor(
    private val dao: RecipeSourceDao
) : RecipeSourceRepository {

    // Add a source (pre or post recipe)
    override suspend fun addSource(source: RecipeSource) {
        dao.insertSource(source)
    }

    // Delete by tempKey (pre-recipe) or recipeId (post-recipe)
    override suspend fun deleteSource(tempKey: Long?, sourceId: Long?) {
        when {
            tempKey != null && sourceId != null -> {
                val sources = dao.getSourcesForTempKey(tempKey)
                sources.find { it.sourceId == sourceId }?.let { dao.deleteSource(it) }
            }
            sourceId != null -> {
                val sources = dao.getSourcesForRecipe(sourceId)
                sources.find { it.sourceId == sourceId }?.let { dao.deleteSource(it) }
            }
        }
    }

    // Get sources for post-recipe
    override suspend fun getSourcesForRecipe(recipeId: Long): List<RecipeSource> {
        return dao.getSourcesForRecipe(recipeId)
    }

    // Get sources for pre-recipe (tempKey)
    override suspend fun getSourcesForTempKey(tempKey: Long): List<RecipeSource> {
        return dao.getSourcesForTempKey(tempKey)
    }
}