package com.example.mealmate.domain.repository

import com.example.mealmate.data.local.Ingredient.IngredientTableEntity
import com.example.mealmate.data.local.Instruction.InstructionTableEntity
import com.example.mealmate.data.local.RecipeSource.RecipeSourceEntity
import com.example.mealmate.domain.model.RecipeTable


interface RecipeRepository {
    suspend fun insertFullRecipe(
        recipe: RecipeTable,
        tempKey: Long
    ): Long
    suspend fun getAllRecipes(userId: Long): List<RecipeTable>

    suspend fun getRecipeById(id: Long): RecipeTable?

    suspend fun deleteRecipe(recipe: RecipeTable)

    suspend fun getRecipesByUser(userId: Long): List<RecipeTable>


    suspend fun getIngredientsForRecipe(recipeId: Long): List<IngredientTableEntity>
    suspend fun getInstructionsForRecipe(recipeId: Long): List<InstructionTableEntity>
    suspend fun getSourcesForRecipe(recipeId: Long): List<RecipeSourceEntity>

}
