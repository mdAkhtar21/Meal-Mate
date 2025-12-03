package com.example.mealmate.presentation.home.addrecipie.RecipeSource

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeSourceBottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    showBottomSheet: MutableState<Boolean>,
    tempKey: Long,
) {
    val viewModel: RecipeSourceViewModel = hiltViewModel()

    val sources by viewModel.sources.collectAsState()

    var name by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }

    LaunchedEffect(showBottomSheet.value) {
        if(showBottomSheet.value){
            viewModel.loadSources(tempKey)
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
                    .padding(horizontal = 16.dp, vertical = 20.dp),
            ) {
                Text(
                    text = "Recipe Source",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = "Enter the name and URL of the recipe source",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Name",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = { Text("Enter the name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "URL",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    TextField(
                        value = url,
                        onValueChange = { url = it },
                        placeholder = { Text("Enter the URL") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (name.isNotBlank() && url.isNotBlank()) {
                            viewModel.addSource(
                                tempKey = tempKey,
                                name = name,
                                url = url
                            )
                            name = ""
                            url = ""
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Add",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
