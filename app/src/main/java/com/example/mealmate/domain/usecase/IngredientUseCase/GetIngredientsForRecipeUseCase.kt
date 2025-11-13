package com.example.mealmate.domain.usecase.IngredientUseCase

import com.example.mealmate.domain.model.Ingredient
import com.example.mealmate.domain.repository.IngredientRepository
import jakarta.inject.Inject

class GetIngredientsForRecipeUseCase @Inject constructor(
    private val repository: IngredientRepository
){
    suspend fun getByTempKey(tempKey: Long): List<Ingredient> {
        return repository.getIngredientsForTempKey(tempKey)
    }

    // Get ingredients by actual recipeId
    suspend fun getByRecipeId(recipeId: Long): List<Ingredient> {
        return repository.getIngredientsForRecipe(recipeId)
    }
}