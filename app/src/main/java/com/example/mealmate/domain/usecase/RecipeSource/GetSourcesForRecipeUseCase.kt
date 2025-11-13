package com.example.mealmate.domain.usecase.RecipeSource

import com.example.mealmate.data.local.AddRecipe.RecipeSource
import com.example.mealmate.domain.repository.RecipeSourceRepository
import javax.inject.Inject


class GetSourcesForRecipeUseCase @Inject constructor(
    private val repository: RecipeSourceRepository
) {
    suspend  fun getByTempKey(tempKey: Long): List<RecipeSource> {
        return repository.getSourcesForTempKey(tempKey)
    }
    suspend  fun getByRecipeId(recipeId: Long): List<RecipeSource> {
        return repository.getSourcesForRecipe(recipeId)
    }
}