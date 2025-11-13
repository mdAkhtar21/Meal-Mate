package com.example.mealmate.domain.usecase.InstructionUseCase

import com.example.mealmate.domain.model.Instruction
import com.example.mealmate.domain.repository.InstructionRepository
import javax.inject.Inject

class GetInstructionsForRecipeUseCase @Inject constructor(
    private val repository: InstructionRepository
) {

    // Get instructions by temporary key
    suspend fun getByTempKey(tempKey: Long): List<Instruction> {
        return repository.getInstructionsForTempKey(tempKey)
    }

    // Get instructions by actual recipeId
    suspend fun getByRecipeId(recipeId: Long): List<Instruction> {
        return repository.getInstructionsForRecipe(recipeId)
    }
}
