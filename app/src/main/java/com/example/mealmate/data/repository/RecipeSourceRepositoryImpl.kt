package com.example.mealmate.data.repository

import com.example.mealmate.data.Mapper.toDomain
import com.example.mealmate.data.local.RecipeSource.RecipeSourceEntity
import com.example.mealmate.data.local.RecipeSource.RecipeSourceDao
import com.example.mealmate.domain.model.RecipeSource
import com.example.mealmate.domain.repository.RecipeSourceRepository
import javax.inject.Inject

class RecipeSourceRepositoryImpl @Inject constructor(
    private val dao: RecipeSourceDao
) : RecipeSourceRepository {

    // Add a source (pre or post recipe)
    override suspend fun addSource(source: RecipeSource) {
        val entity=RecipeSourceEntity(
            sourceId=source.sourceId,
            tempKey=source.tempKey,
            recipeId=source.recipeId,
            name = source.name,
            url = source.url
        )

        dao.insertSource(entity)
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
        return dao.getSourcesForRecipe(recipeId).map { it.toDomain() }
    }

    // Get sources for pre-recipe (tempKey)
    override suspend fun getSourcesForTempKey(tempKey: Long): List<RecipeSource> {
        return dao.getSourcesForTempKey(tempKey).map { it.toDomain() }
    }
}