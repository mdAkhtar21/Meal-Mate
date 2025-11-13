package com.example.mealmate.domain.usecase.IngredientUseCase

import com.example.mealmate.domain.repository.IngredientRepository
import jakarta.inject.Inject

class DeleteIngredientUseCase @Inject constructor(
    private val repository: IngredientRepository
) {
    // Delete ingredient by recipeId and ingredientId
    suspend operator fun invoke(tempKey: Long, ingredientId: Long) {
        repository.deleteIngredient(tempKey, ingredientId)
    }
}
