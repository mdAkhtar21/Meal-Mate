package com.example.mealmate.domain.usecase.InstructionUseCase

import com.example.mealmate.domain.model.Instruction
import com.example.mealmate.domain.repository.InstructionRepository
import jakarta.inject.Inject

class UpdateInstructionUseCase @Inject constructor(
    private val repository: InstructionRepository
) {
    suspend operator fun invoke(instruction: Instruction) {
        repository.updateInstruction(instruction)
    }
}