package com.example.mealmate.presentation.home.addrecipie.instruction

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.items
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mealmate.domain.model.Instruction
import com.example.mealmate.presentation.common.SwipeableInstructionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionBottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    showBottomSheet: MutableState<Boolean>,
    tempKey: Long,
) {
    var instructionText by remember { mutableStateOf("") }
    val viewModel: InstructionViewModel = hiltViewModel()
    val instructions by viewModel.instructions.collectAsState()
    var editInstruction by remember { mutableStateOf<Instruction?>(null) }

    LaunchedEffect(showBottomSheet.value) {
        if (showBottomSheet.value){
            viewModel.loadInstructions(tempKey)
        }
    }

    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet.value = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                Text(
                    text = "Instruction",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Tap to add and swipe to delete instruction",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    value = instructionText,
                    onValueChange = { instructionText = it },
                    placeholder = { Text(text = "Enter the Instruction") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (instructionText.isNotBlank()) {
                                if (editInstruction == null) {
                                    viewModel.addInstruction(
                                        tempKey,
                                        step = instructions.size + 1,
                                        value = instructionText
                                    )
                                }
                                else {
                                    viewModel.updateInstruction(
                                        editInstruction!!.copy(
                                            value = instructionText,
                                            tempKey = tempKey
                                        )
                                    )
                                    editInstruction = null
                                }
                                instructionText = ""
                            }
                        }
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(bottom = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items = instructions) { instruction ->
                        SwipeableInstructionItem(
                            instruction = instruction,
                            viewModel = viewModel,
                            onEdit = {
                                editInstruction = instruction
                                instructionText = instruction.value
                            },
                            recipeId = tempKey
                        )
                    }
                }
            }
        }
    }
}
