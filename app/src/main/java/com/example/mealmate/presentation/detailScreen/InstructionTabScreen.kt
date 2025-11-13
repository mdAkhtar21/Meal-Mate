package com.example.mealmate.presentation.detailScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mealmate.data.local.AddRecipe.InstructionTableEntity

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
            .fillMaxSize()
            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
    ) {

        // Cooking Time Row
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Color.Black.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "${recipeDetail?.recipe?.cookingTime ?: "--"} Cooking Time",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black.copy(alpha = 0.7f)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(recipeSources.size) { index ->
                val source = recipeSources[index]

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = source.name ?: "No Name",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.W600,
                        color = Color.Black.copy(alpha = 0.6f)
                    )
                    val url = source.url ?: ""

                    Text(
                        text = if (url.isNotBlank()) url else "No URL",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.W500,
                            textDecoration = if (url.startsWith("http")) TextDecoration.Underline else TextDecoration.None
                        ),
                        color = if (url.startsWith("http")) Color.Blue else Color.Gray,
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .clickable(enabled = url.startsWith("http")) {
                                if (url.startsWith("http")) {
                                    uriHandler.openUri(url)
                                }
                            }
                    )
                }
            }
            items(instructions.size) { index ->
                instructionlistItem(instructions[index])
            }
        }
    }
}

@Composable
fun instructionlistItem(instruction: InstructionTableEntity) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = instruction.description,
            fontSize = 18.sp,
            color = Color.Black
        )
    }
}
