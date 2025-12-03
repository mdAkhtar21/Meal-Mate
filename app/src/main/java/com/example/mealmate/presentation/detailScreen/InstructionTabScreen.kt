package com.example.mealmate.presentation.detailScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mealmate.R
import com.example.mealmate.data.local.Instruction.InstructionTableEntity
import com.example.mealmate.domain.model.Instruction


@Composable
fun InstructionTabScreen(
    recipeId: Long?,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val recipeDetail = viewModel.recipeDetail.collectAsState().value
    val instructions = recipeDetail?.instructions?.filter { it.recipeId == recipeId } ?: emptyList()
    val recipeSources = recipeDetail?.sources?.filter { it.recipeId == recipeId } ?: emptyList()
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
    ) {

        // --- Instructions ---
        instructions.forEachIndexed { index, instruction ->
            instructionlistItem(instruction)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color.Gray.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Source",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Gray.copy(alpha = 0.7f)
        )


        recipeSources.forEach { source ->
            Column(modifier = Modifier.fillMaxWidth()) {

                val url = source.url ?: ""

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 2.dp)
                ) {

                    if (url.startsWith("http")) {
                        Icon(
                            painter = painterResource(id = R.drawable.link),
                            contentDescription = "Open Link",
                            tint = Color.Blue,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    uriHandler.openUri(url)
                                }
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = source.name ?: "No Name",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.W600,
                        color = Color.Black.copy(alpha = 0.6f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}



@Composable
fun instructionlistItem(instruction: Instruction) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = instruction.value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
    }
}

