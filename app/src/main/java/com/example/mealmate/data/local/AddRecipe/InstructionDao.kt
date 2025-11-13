package com.example.mealmate.data.local.AddRecipe


import androidx.room.*

@Dao
interface InstructionDao {

    @Insert
    suspend fun insertInstruction(instruction: InstructionTableEntity)

    @Insert
    suspend fun insertInstructions(instructions: List<InstructionTableEntity>)

    @Update
    suspend fun updateInstruction(instruction: InstructionTableEntity)

    @Delete
    suspend fun deleteInstruction(instruction: InstructionTableEntity)

    // ✅ Delete instruction by tempKey and instructionId
    @Query("DELETE FROM instructions WHERE tempKey = :tempKey AND instructionId = :instructionId")
    suspend fun deleteInstruction(tempKey: Long, instructionId: Long)

    // ✅ Get all instructions by tempKey (before recipe exists)
    @Query("SELECT * FROM instructions WHERE tempKey = :tempKey ORDER BY step ASC")
    suspend fun getInstructionsForTempKey(tempKey: Long): List<InstructionTableEntity>

    // Optional: get instructions by recipeId (after recipe is saved)
    @Query("SELECT * FROM instructions WHERE recipeId = :recipeId ORDER BY step ASC")
    suspend fun getInstructionsForRecipe(recipeId: Long): List<InstructionTableEntity>

    @Query("DELETE FROM instructions WHERE tempKey = :tempKey")
    suspend fun deleteInstructionsForTempKey(tempKey: Long)
    @Query("UPDATE instructions SET recipeId = :recipeId, tempKey = NULL WHERE tempKey = :tempKey")
    suspend fun commitTempInstructionsToRecipe(tempKey: Long, recipeId: Long)

}
