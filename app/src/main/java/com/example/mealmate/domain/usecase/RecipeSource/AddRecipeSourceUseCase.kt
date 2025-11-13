package com.example.mealmate.domain.usecase.RecipeSource

import com.example.mealmate.data.local.AddRecipe.RecipeSource
import com.example.mealmate.domain.repository.RecipeSourceRepository
import javax.inject.Inject


class AddRecipeSourceUseCase @Inject constructor(
    private val repository: RecipeSourceRepository
) {
    suspend operator fun invoke(source: RecipeSource) {
        repository.addSource(source)
    }
}