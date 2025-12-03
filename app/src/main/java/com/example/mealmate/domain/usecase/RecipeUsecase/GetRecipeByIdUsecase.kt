package com.example.mealmate.domain.usecase.RecipeUsecase

import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity
import com.example.mealmate.domain.model.RecipeTable
import com.example.mealmate.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipeByIdUsecase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(recipeId: Long): RecipeTable?{
        return repository.getRecipeById(recipeId)
    }
}
