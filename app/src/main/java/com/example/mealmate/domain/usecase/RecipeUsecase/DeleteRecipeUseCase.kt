package com.example.mealmate.domain.usecase.RecipeUsecase

import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity
import com.example.mealmate.domain.repository.RecipeRepository
import jakarta.inject.Inject

class DeleteRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(recipe: RecipeTableEntity) {
        repository.deleteRecipe(recipe)
    }
}