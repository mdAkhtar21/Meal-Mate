package com.example.mealmate.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealmate.domain.model.Instruction
import com.example.mealmate.presentation.home.addrecipie.instruction.InstructionViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun SwipeableInstructionItem(
    instruction: Instruction,
    viewModel: InstructionViewModel,
    onEdit: () -> Unit,
    recipeId: Long,
) {
    val deleteAction = SwipeAction(
        onSwipe = { viewModel.deleteInstruction(recipeId, instruction.instructionId) },
        icon = {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        },
        background = Color.Red
    )

    val editAction = SwipeAction(
        onSwipe = onEdit ,
        icon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                tint = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        },
        background = Color.Blue
    )

    SwipeableActionsBox(
        startActions = listOf(editAction),
        endActions = listOf(deleteAction)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = "Step ${instruction.step}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = instruction.value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
    }
}
