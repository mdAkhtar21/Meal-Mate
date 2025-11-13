package com.example.mealmate.data.repository

import com.example.mealmate.data.local.AddRecipe.InstructionDao
import com.example.mealmate.data.local.AddRecipe.InstructionTableEntity
import com.example.mealmate.domain.model.Instruction
import com.example.mealmate.domain.repository.InstructionRepository

class InstructionRepositoryImpl(
    private val dao: InstructionDao
) : InstructionRepository {

    override suspend fun addInstruction(instruction: Instruction) {
        dao.insertInstruction(instruction.toEntity())
    }

    override suspend fun updateInstruction(instruction: Instruction) {
        dao.updateInstruction(instruction.toEntity())
    }

    override suspend fun deleteInstruction(tempKey: Long?, instructionId: Long?) {
        if (tempKey != null && instructionId != null) {
            val entity = dao.getInstructionsForTempKey(tempKey)
                .firstOrNull { it.instructionId == instructionId }
            entity?.let { dao.deleteInstruction(it) }
        }
    }

    override suspend fun getInstructionsForTempKey(tempKey: Long): List<Instruction> {
        return dao.getInstructionsForTempKey(tempKey).map { it.toDomain() }
    }

    override suspend fun getInstructionsForRecipe(recipeId: Long): List<Instruction> {
        return dao.getInstructionsForRecipe(recipeId).map { it.toDomain() }
    }
}

// --- Mapping extensions ---
private fun Instruction.toEntity(): InstructionTableEntity =
    InstructionTableEntity(
        instructionId = instructionId,
        tempKey = tempKey,
        recipeId = recipeId,
        step = step,
        description = value
    )

private fun InstructionTableEntity.toDomain(): Instruction =
    Instruction(
        instructionId = instructionId,
        tempKey = tempKey,
        recipeId = recipeId,
        step = step,
        value = description
    )
