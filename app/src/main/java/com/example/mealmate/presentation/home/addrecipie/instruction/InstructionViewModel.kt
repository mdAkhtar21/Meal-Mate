package com.example.mealmate.presentation.home.addrecipie.instruction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.Instruction
import com.example.mealmate.domain.usecase.InstructionUseCase.AddInstructionUseCase
import com.example.mealmate.domain.usecase.InstructionUseCase.DeleteInstructionUseCase
import com.example.mealmate.domain.usecase.InstructionUseCase.GetInstructionsForRecipeUseCase
import com.example.mealmate.domain.usecase.InstructionUseCase.UpdateInstructionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstructionViewModel @Inject constructor(
    private val addInstructionUseCase: AddInstructionUseCase,
    private val deleteInstructionUseCase: DeleteInstructionUseCase,
    private val getInstructionsForRecipeUseCase: GetInstructionsForRecipeUseCase,
    private val updateInstructionUseCase: UpdateInstructionUseCase
) : ViewModel() {

    private val _instructions = MutableStateFlow<List<Instruction>>(emptyList())
    val instructions: StateFlow<List<Instruction>> = _instructions

    fun loadInstructions(tempKey: Long) {
        viewModelScope.launch {
            _instructions.value = getInstructionsForRecipeUseCase.getByTempKey(tempKey)
        }
    }

    fun addInstruction(tempKey:Long, step: Int, value: String) {
        viewModelScope.launch {
            val instruction = Instruction(
                recipeId =null ,
                tempKey = tempKey,
                step = step,
                value = value
            )
            addInstructionUseCase(instruction)
            loadInstructions(tempKey)
        }
    }

    fun deleteInstruction(tempKey:Long, instructionId: Long) {
        viewModelScope.launch {
            deleteInstructionUseCase(tempKey, instructionId)
            loadInstructions(tempKey)
        }
    }

    fun updateInstruction(instruction: Instruction) {
        viewModelScope.launch {
            val key = instruction.tempKey?: return@launch
            val updatedInstruction= instruction.copy(
                tempKey = key
            )
            updateInstructionUseCase(updatedInstruction)
            loadInstructions(key)
        }
    }
}