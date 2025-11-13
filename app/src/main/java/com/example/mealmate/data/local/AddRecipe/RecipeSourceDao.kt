package com.example.mealmate.data.local.AddRecipe

import androidx.room.*

@Dao
interface RecipeSourceDao {

    @Insert
    suspend fun insertSource(source: RecipeSource)

    @Insert
    suspend fun insertSources(sources: List<RecipeSource>)

    @Update
    suspend fun updateSource(source: RecipeSource)

    @Delete
    suspend fun deleteSource(source: RecipeSource)

    // ✅ Delete a source by tempKey and sourceId
    @Query("DELETE FROM recipe_sources WHERE tempKey = :tempKey AND sourceId = :sourceId")
    suspend fun deleteSource(tempKey: Long, sourceId: Long)

    // ✅ Get sources by tempKey (before recipe exists)
    @Query("SELECT * FROM recipe_sources WHERE tempKey = :tempKey")
    suspend fun getSourcesForTempKey(tempKey: Long): List<RecipeSource>

    // Optional: get sources by recipeId (after recipe is saved)
    @Query("SELECT * FROM recipe_sources WHERE recipeId = :recipeId")
    suspend fun getSourcesForRecipe(recipeId: Long): List<RecipeSource>

    @Query("DELETE FROM recipe_sources WHERE tempKey = :tempKey")
    suspend fun deleteSourcesForTempKey(tempKey: Long)

    @Query("UPDATE recipe_sources SET recipeId = :recipeId, tempKey = NULL WHERE tempKey = :tempKey")
    suspend fun commitTempSourcesToRecipe(tempKey: Long, recipeId: Long)

}
