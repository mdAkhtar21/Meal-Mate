package com.example.mealmate.domain.usecase.IngredientUseCase

import com.example.mealmate.domain.model.Ingredient
import com.example.mealmate.domain.repository.IngredientRepository
import jakarta.inject.Inject

class AddIngredientUseCase @Inject constructor(
    private val repository: IngredientRepository
) {
    suspend operator fun invoke(ingredient: Ingredient) {
        repository.addIngredient(ingredient)
    }
}