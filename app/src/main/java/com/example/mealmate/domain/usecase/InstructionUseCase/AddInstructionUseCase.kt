package com.example.mealmate.domain.usecase.InstructionUseCase

import com.example.mealmate.domain.model.Instruction
import com.example.mealmate.domain.repository.InstructionRepository
import javax.inject.Inject

class AddInstructionUseCase @Inject constructor(
    private val repository: InstructionRepository
) {
    suspend operator fun invoke(instruction: Instruction) {
        repository.addInstruction(instruction)
    }
}