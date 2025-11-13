package com.example.mealmate.domain.usecase.RecipeUsecase

import com.example.mealmate.data.local.AddRecipe.IngredientTableEntity
import com.example.mealmate.data.local.AddRecipe.InstructionTableEntity
import com.example.mealmate.data.local.AddRecipe.RecipeSource
import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity
import com.example.mealmate.domain.repository.RecipeRepository
import jakarta.inject.Inject

data class FullRecipeDetail(
    val recipe: RecipeTableEntity,
    val ingredients: List<IngredientTableEntity>,
    val instructions: List<InstructionTableEntity>,
    val sources: List<RecipeSource>
)

class GetRecipeDetailUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(recipeId: Long): FullRecipeDetail? {
        val recipe = repository.getRecipeById(recipeId) ?: return null
        val ingredients = repository.getIngredientsForRecipe(recipeId)
        val instructions = repository.getInstructionsForRecipe(recipeId)
        val sources = repository.getSourcesForRecipe(recipeId)
        return FullRecipeDetail(recipe, ingredients, instructions, sources)
    }
}