package com.example.mealmate.data.local.AddRecipe


import androidx.room.*

@Dao
interface IngredientDao {

    @Insert
    suspend fun insertIngredient(ingredient: IngredientTableEntity)

    @Insert
    suspend fun insertIngredients(ingredients: List<IngredientTableEntity>)

    @Update
    suspend fun updateIngredient(ingredient: IngredientTableEntity)

    @Delete
    suspend fun deleteIngredient(ingredient: IngredientTableEntity)

    // ✅ Delete a specific ingredient by tempKey and ingredientId
    @Query("DELETE FROM ingredients WHERE tempKey = :tempKey AND ingredientId = :ingredientId")
    suspend fun deleteIngredient(tempKey: Long, ingredientId: Long)

    // ✅ Get all ingredients for a tempKey
    @Query("SELECT * FROM ingredients WHERE tempKey = :tempKey")
    suspend fun getIngredientsForTempKey(tempKey: Long): List<IngredientTableEntity>

    @Query("SELECT * FROM ingredients WHERE recipeId = :recipeId")
    suspend fun getIngredientsForRecipe(recipeId: Long): List<IngredientTableEntity>

    @Query("DELETE FROM ingredients WHERE tempKey = :tempKey")
    suspend fun deleteIngredientsForTempKey(tempKey: Long)

    /**
     * 🔄 Commit: Updates all temporary ingredients to link them to the newly created recipe ID.
     * This method is crucial if you do NOT delete the temporary ingredients but rather update them.
     */
    @Query("UPDATE ingredients SET recipeId = :recipeId, tempKey = NULL WHERE tempKey = :tempKey")
    suspend fun commitTempIngredientsToRecipe(tempKey: Long, recipeId: Long)


}
