package com.example.mealmate.data.repository

import android.util.Log
import com.example.mealmate.data.Mapper.toDomain
import com.example.mealmate.data.Mapper.toEntity
import com.example.mealmate.data.local.Ingredient.IngredientDao
import com.example.mealmate.data.local.Ingredient.IngredientTableEntity
import com.example.mealmate.data.local.Instruction.InstructionDao
import com.example.mealmate.data.local.Instruction.InstructionTableEntity
import com.example.mealmate.data.local.AddRecipe.RecipeDao
import com.example.mealmate.data.local.RecipeSource.RecipeSourceEntity
import com.example.mealmate.data.local.RecipeSource.RecipeSourceDao
import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity
import com.example.mealmate.domain.model.RecipeTable
import com.example.mealmate.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
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
        recipe: RecipeTable,
        tempKey: Long
    ): Long {
        val recipeId = recipeDao.insertRecipe(recipe.toEntity())
        val beforeUpdate = ingredientDao.getIngredientsForTempKey(tempKey)
        Log.d("RecipeDebug", "Before commit: found ${beforeUpdate.size} ingredients for tempKey=$tempKey")
        val updatedRows = ingredientDao.commitTempIngredientsToRecipe(tempKey, recipeId)
        instructionDao.commitTempInstructionsToRecipe(tempKey, recipeId)
        sourceDao.commitTempSourcesToRecipe(tempKey, recipeId)

        Log.d("RecipeDebug", "Updated $updatedRows ingredients for recipeId=$recipeId")

        // 4️⃣ Confirm after update
        val afterUpdate = ingredientDao.getIngredientsForRecipe(recipeId)
        Log.d("RecipeDebug", "After commit: found ${afterUpdate.size} ingredients for recipeId=$recipeId")

        return recipeId
    }


    override suspend fun getAllRecipes(userId: Long): List<RecipeTable> {
        return recipeDao.getRecipesByUser(userId)
            .first()
            .map { it.toDomain() }
    }

    override suspend fun getRecipeById(id: Long): RecipeTable? =
        recipeDao.getRecipeById(id)?.toDomain()

    override suspend fun deleteRecipe(recipe: RecipeTable){
        val entity=RecipeTableEntity(
            recipeId = recipe.recipeId,
            userId = recipe.userId,
            title = recipe.title,
            description = recipe.description,
            recipeImage = recipe.recipeImage,
            category = recipe.category,
            style = recipe.style,
            mealType = recipe.mealType,
            serving = recipe.serving,
            preprationTime = recipe.preprationTime,
            cookingTime = recipe.cookingTime
        )
        recipeDao.deleteRecipe(entity)
    }

    override suspend fun getRecipesByUser(userId: Long): List<RecipeTable> {
        val result = recipeDao.getRecipesByUser(userId).first()
        return result.map { it.toDomain() }
    }


    override suspend fun getIngredientsForRecipe(recipeId: Long): List<IngredientTableEntity> =
        recipeDao.getIngredientsForRecipe(recipeId)

    override suspend fun getInstructionsForRecipe(recipeId: Long): List<InstructionTableEntity> =
        recipeDao.getInstructionsForRecipe(recipeId)

    override suspend fun getSourcesForRecipe(recipeId: Long): List<RecipeSourceEntity> =
        recipeDao.getSourcesForRecipe(recipeId)

}
