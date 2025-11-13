package com.example.mealmate.data.local.AddRecipe

import androidx.room.*

@Dao
interface RecipeDao {

    // ─── Basic CRUD ───────────────────────────
    @Insert
    suspend fun insertRecipe(recipe: RecipeTableEntity): Long

    @Update
    suspend fun updateRecipe(recipe: RecipeTableEntity)

    @Delete
    suspend fun deleteRecipe(recipe: RecipeTableEntity)

    @Query("SELECT * FROM recipes WHERE recipeId = :id")
    suspend fun getRecipeById(id: Long): RecipeTableEntity?

    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<RecipeTableEntity>


    @Transaction
    suspend fun insertFullRecipe(
        recipe: RecipeTableEntity,
        ingredients: List<IngredientTableEntity>,
        instructions: List<InstructionTableEntity>,
        sources: List<RecipeSource>
    ): Long {
        val recipeId = insertRecipe(recipe)
        val ingredientsWithId = ingredients.map {
            it.copy(
                ingredientId = 0L, // <--- RESET ID
                recipeId = recipeId
            )
        }
        val instructionsWithId = instructions.map {
            it.copy(
                instructionId = 0L,
                recipeId = recipeId
            )
        }
        val sourcesWithId = sources.map {
            it.copy(
                sourceId = 0L, // <--- RESET ID (Assuming RecipeSource has sourceId)
                recipeId = recipeId
            )
        }
        insertIngredients(ingredientsWithId)
        insertInstructions(instructionsWithId)
        insertSources(sourcesWithId)
        return recipeId
    }



    @Insert
    suspend fun insertIngredients(ingredients: List<IngredientTableEntity>)

    @Insert
    suspend fun insertInstructions(instructions: List<InstructionTableEntity>)

    @Insert
    suspend fun insertSources(sources: List<RecipeSource>)

    // Methods for FullRecipeDetail
    @Query("SELECT * FROM ingredients WHERE recipeId = :recipeId")
    suspend fun getIngredientsForRecipe(recipeId: Long): List<IngredientTableEntity>

    @Query("SELECT * FROM instructions WHERE recipeId = :recipeId")
    suspend fun getInstructionsForRecipe(recipeId: Long): List<InstructionTableEntity>

    // Fix here: use correct table name
    @Query("SELECT * FROM recipe_sources WHERE recipeId = :recipeId")
    suspend fun getSourcesForRecipe(recipeId: Long): List<RecipeSource>
}
