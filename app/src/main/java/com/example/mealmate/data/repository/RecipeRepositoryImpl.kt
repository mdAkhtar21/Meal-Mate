package com.example.mealmate.data.repository

import android.util.Log
import com.example.mealmate.data.local.AddRecipe.IngredientDao
import com.example.mealmate.data.local.AddRecipe.IngredientTableEntity
import com.example.mealmate.data.local.AddRecipe.InstructionDao
import com.example.mealmate.data.local.AddRecipe.InstructionTableEntity
import com.example.mealmate.data.local.AddRecipe.RecipeDao
import com.example.mealmate.data.local.AddRecipe.RecipeSource
import com.example.mealmate.data.local.AddRecipe.RecipeSourceDao
import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity
import com.example.mealmate.domain.repository.RecipeRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao,
    private val ingredientDao: IngredientDao,
    private val instructionDao: InstructionDao,
    private val sourceDao: RecipeSourceDao
) : RecipeRepository {

    override suspend fun insertFullRecipe(
        recipe: RecipeTableEntity,
        tempKey: Long
    ): Long {
        // 1️⃣ Insert recipe and get new recipeId
        val recipeId = recipeDao.insertRecipe(recipe)

        // 2️⃣ Check what exists before updating
        val beforeUpdate = ingredientDao.getIngredientsForTempKey(tempKey)
        Log.d("RecipeDebug", "Before commit: found ${beforeUpdate.size} ingredients for tempKey=$tempKey")

        // 3️⃣ Update all temp data (link recipeId and remove tempKey)
        val updatedRows = ingredientDao.commitTempIngredientsToRecipe(tempKey, recipeId)
        instructionDao.commitTempInstructionsToRecipe(tempKey, recipeId)
        sourceDao.commitTempSourcesToRecipe(tempKey, recipeId)

        Log.d("RecipeDebug", "Updated $updatedRows ingredients for recipeId=$recipeId")

        // 4️⃣ Confirm after update
        val afterUpdate = ingredientDao.getIngredientsForRecipe(recipeId)
        Log.d("RecipeDebug", "After commit: found ${afterUpdate.size} ingredients for recipeId=$recipeId")

        return recipeId
    }


    override suspend fun getAllRecipes(): List<RecipeTableEntity> =
        recipeDao.getAllRecipes()

    override suspend fun getRecipeById(id: Long): RecipeTableEntity? =
        recipeDao.getRecipeById(id)

    override suspend fun deleteRecipe(recipe: RecipeTableEntity) =
        recipeDao.deleteRecipe(recipe)


    override suspend fun getIngredientsForRecipe(recipeId: Long): List<IngredientTableEntity> =
        recipeDao.getIngredientsForRecipe(recipeId)

    override suspend fun getInstructionsForRecipe(recipeId: Long): List<InstructionTableEntity> =
        recipeDao.getInstructionsForRecipe(recipeId)

    override suspend fun getSourcesForRecipe(recipeId: Long): List<RecipeSource> =
        recipeDao.getSourcesForRecipe(recipeId)

}
