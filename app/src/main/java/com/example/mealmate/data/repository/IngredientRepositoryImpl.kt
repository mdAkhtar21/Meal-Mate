package com.example.mealmate.data.repository

import com.example.mealmate.data.local.AddRecipe.IngredientDao
import com.example.mealmate.data.local.AddRecipe.IngredientTableEntity
import com.example.mealmate.domain.model.Ingredient
import com.example.mealmate.domain.repository.IngredientRepository

class IngredientRepositoryImpl(
    private val dao: IngredientDao
) : IngredientRepository {

    override suspend fun addIngredient(ingredient: Ingredient) {
        dao.insertIngredient(ingredient.toEntity())
    }

    override suspend fun deleteIngredient(tempKey: Long?, ingredientId: Long?) {
        if (tempKey != null && ingredientId != null) {
            dao.deleteIngredient(tempKey, ingredientId)
        }
    }

    override suspend fun getIngredientsForTempKey(tempKey: Long): List<Ingredient> {
        return dao.getIngredientsForTempKey(tempKey).map { it.toDomain() }
    }

    override suspend fun getIngredientsForRecipe(recipeId: Long): List<Ingredient> {
        return dao.getIngredientsForRecipe(recipeId).map { it.toDomain() }
    }
}

// --- Mapping Extensions ---

private fun Ingredient.toEntity(): IngredientTableEntity {
    return IngredientTableEntity(
        ingredientId = ingredientId,
        tempKey = tempKey,
        recipeId = recipeId,
        name = name,
        category = category,
        measurement = measurement
    )
}

private fun IngredientTableEntity.toDomain(): Ingredient {
    return Ingredient(
        ingredientId = ingredientId,
        tempKey = tempKey,
        recipeId = recipeId,
        name = name,
        category = category,
        measurement = measurement
    )
}
