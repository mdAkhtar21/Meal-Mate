package com.example.mealmate.domain.usecase.RecipeSource

import com.example.mealmate.data.local.RecipeSource.RecipeSourceEntity
import com.example.mealmate.domain.model.RecipeSource
import com.example.mealmate.domain.repository.RecipeSourceRepository
import javax.inject.Inject


class AddRecipeSourceUseCase @Inject constructor(
    private val repository: RecipeSourceRepository
) {
    suspend operator fun invoke(source: RecipeSource) {
        repository.addSource(source)
    }
}