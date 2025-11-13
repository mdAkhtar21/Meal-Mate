package com.example.mealmate.domain.usecase.InstructionUseCase

import com.example.mealmate.domain.repository.InstructionRepository
import jakarta.inject.Inject

class DeleteInstructionUseCase @Inject constructor(
    private val repository: InstructionRepository
) {
    suspend operator fun invoke(tempKey: Long, instructionId: Long) {
        repository.deleteInstruction(tempKey, instructionId)
    }
}