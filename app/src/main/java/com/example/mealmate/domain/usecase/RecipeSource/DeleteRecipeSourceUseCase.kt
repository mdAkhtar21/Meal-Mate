package com.example.mealmate.domain.usecase.RecipeSource

import com.example.mealmate.domain.repository.RecipeSourceRepository
import jakarta.inject.Inject

class DeleteRecipeSourceUseCase @Inject constructor(
    private val repository: RecipeSourceRepository
) {
    suspend operator fun invoke(tempKey:Long,sourceId: Long) {
        repository.deleteSource(tempKey,sourceId)
    }
}