package com.example.mealmate.domain.usecase.RecipeUsecase

import com.example.mealmate.data.Mapper.toDomain
import com.example.mealmate.domain.model.FullRecipeDetailDomain
import com.example.mealmate.domain.repository.RecipeRepository
import jakarta.inject.Inject



class GetRecipeDetailUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(recipeId: Long): FullRecipeDetailDomain? {
        val recipe = repository.getRecipeById(recipeId) ?: return null
        val ingredients = repository.getIngredientsForRecipe(recipeId)
        val instructions = repository.getInstructionsForRecipe(recipeId)
        val sources = repository.getSourcesForRecipe(recipeId)
        return FullRecipeDetailDomain(
            recipe = recipe,
            ingredients = ingredients.map { it.toDomain() },
            instructions = instructions.map { it.toDomain() },
            sources = sources.map { it.toDomain() }
        )
    }
}