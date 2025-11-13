package com.example.mealmate.domain.repository

import com.example.mealmate.domain.model.Instruction


interface InstructionRepository {

    // Add instruction (pre or post recipe)
    suspend fun addInstruction(instruction: Instruction)

    // Update instruction
    suspend fun updateInstruction(instruction: Instruction)

    // Delete instruction by tempKey (pre-recipe) or recipeId (post-recipe)
    suspend fun deleteInstruction(tempKey: Long?, instructionId: Long?)

    // Get instructions for a specific recipe (post-recipe)
    suspend fun getInstructionsForRecipe(recipeId: Long): List<Instruction>

    // Get instructions for a pre-recipe using tempKey
    suspend fun getInstructionsForTempKey(tempKey: Long): List<Instruction>
}