package com.example.mealmate.domain.usecase.RecipeUsecase

import com.example.mealmate.data.local.AddRecipe.IngredientTableEntity
import com.example.mealmate.data.local.AddRecipe.InstructionTableEntity
import com.example.mealmate.data.local.AddRecipe.RecipeSource
import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity
import com.example.mealmate.domain.repository.RecipeRepository
import jakarta.inject.Inject

class InsertRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(
        recipe: RecipeTableEntity,
        tempKey: Long // Only pass the main recipe and the tempKey
    ): Long {
        // The repository is now responsible for fetching the ingredients/instructions/sources
        // using this tempKey, performing the transaction, and returning the new recipe ID.
        return repository.insertFullRecipe(recipe, tempKey)
    }
}