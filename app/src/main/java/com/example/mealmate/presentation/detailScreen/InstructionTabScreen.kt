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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mealmate.R
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
//                Text(
//                    text = source.name ?: "No Name",
//                    style = MaterialTheme.typography.bodyMedium,
//                    fontWeight = FontWeight.W600,
//                    color = Color.Black.copy(alpha = 0.6f)
//                )

                val url = source.url ?: ""

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 2.dp)
                ) {
//                    Text(
//                        text = if (url.isNotBlank()) url else "No URL",
//                        style = MaterialTheme.typography.bodyMedium.copy(
//                            fontWeight = FontWeight.W500,
//                            textDecoration = if (url.startsWith("http")) TextDecoration.Underline else TextDecoration.None
//                        ),
//                        color = if (url.startsWith("http")) Color.Blue else Color.Gray,
//                        modifier = Modifier.weight(1f) // fill remaining space
//                    )

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

//        // --- Sources ---
//        recipeSources.forEach { source ->
//            Column(modifier = Modifier.fillMaxWidth()) {
//                Text(
//                    text = source.name ?: "No Name",
//                    style = MaterialTheme.typography.bodyMedium,
//                    fontWeight = FontWeight.W600,
//                    color = Color.Black.copy(alpha = 0.6f)
//                )
//
//                val url = source.url ?: ""
//
//                Text(
//                    text = if (url.isNotBlank()) url else "No URL",
//                    style = MaterialTheme.typography.bodyMedium.copy(
//                        fontWeight = FontWeight.W500,
//                        textDecoration = if (url.startsWith("http")) TextDecoration.Underline else TextDecoration.None
//                    ),
//                    color = if (url.startsWith("http")) Color.Blue else Color.Gray,
//                    modifier = Modifier
//                        .padding(top = 2.dp)
//                        .clickable(enabled = url.startsWith("http")) {
//                            if (url.startsWith("http")) {
//                                uriHandler.openUri(url)
//                            }
//                        }
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//
//    }
//}

@Composable
fun instructionlistItem(instruction: InstructionTableEntity) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = instruction.description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
    }
}



//@Composable
//fun InstructionTabScreen(
//    recipeId: Long?,
//    viewModel: DetailViewModel = hiltViewModel()
//) {
//    val recipeDetail = viewModel.recipeDetail.collectAsState().value
//    val instructions = recipeDetail?.instructions?.filter { it.recipeId == recipeId } ?: emptyList()
//    val recipeSources = recipeDetail?.sources?.filter { it.recipeId == recipeId } ?: emptyList()
//    val uriHandler = LocalUriHandler.current
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
//    ) {
//        LazyColumn(
//            modifier = Modifier.fillMaxSize(),
//            contentPadding = PaddingValues(vertical = 8.dp)
//        ) {
//            items(recipeSources.size) { index ->
//                val source = recipeSources[index]
//
//                Column(modifier = Modifier.fillMaxWidth()) {
//                    Text(
//                        text = source.name ?: "No Name",
//                        style = MaterialTheme.typography.bodyMedium,
//                        fontWeight = FontWeight.W600,
//                        color = Color.Black.copy(alpha = 0.6f)
//                    )
//                    val url = source.url ?: ""
//
//                    Text(
//                        text = if (url.isNotBlank()) url else "No URL",
//                        style = MaterialTheme.typography.bodyMedium.copy(
//                            fontWeight = FontWeight.W500,
//                            textDecoration = if (url.startsWith("http")) TextDecoration.Underline else TextDecoration.None
//                        ),
//                        color = if (url.startsWith("http")) Color.Blue else Color.Gray,
//                        modifier = Modifier
//                            .padding(top = 2.dp)
//                            .clickable(enabled = url.startsWith("http")) {
//                                if (url.startsWith("http")) {
//                                    uriHandler.openUri(url)
//                                }
//                            }
//                    )
//                }
//            }
//            items(instructions.size) { index ->
//                instructionlistItem(instructions[index])
//            }
//        }
//    }
//}
//
//@Composable
//fun instructionlistItem(instruction: InstructionTableEntity) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            text = instruction.description,
//            fontSize = 18.sp,
//            color = Color.Black
//        )
//    }
//}
