package com.example.mealmate.presentation.home.addrecipie.ingredient

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientBottomSheet2(
    tempKey: Long,
    sheetState: SheetState,
    scope: CoroutineScope,
    showBottomSheet: MutableState<Boolean>,
    showNextSheet: MutableState<Boolean> // New state for next sheet
) {
    val viewModel: IngredientViewModel = hiltViewModel()
    var expanded by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var measurement by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("Baking") }

    val options = listOf(
        "Baking", "Bread and Bakery", "Condiments", "Dairy and Eggs",
        "Frozen", "Fruits and Vegetables", "Herbs and Spices",
        "Household", "Meals and Safe Food", "Pasta, Rice and Beans"
    )

    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet.value = false },
            sheetState = sheetState
        ) {
            // If the next sheet should show, render it
            if (showNextSheet.value) {
                IngredientBottomSheet(
                    sheetState = sheetState,
                    scope = scope,
                    showIngredientSheet = showBottomSheet,
                    tempKey = tempKey
                )
            } else {
                // Current sheet content
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Add Ingredient",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Dropdown
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            value = selectedOption,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Ingredient Category") },
                            trailingIcon = {
                                Icon(
                                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier.menuAnchor().fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            options.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option, style = MaterialTheme.typography.bodyMedium) },
                                    onClick = {
                                        selectedOption = option
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Name input
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Ingredient Name") },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter the name") },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Measurement input
                    OutlinedTextField(
                        value = measurement,
                        onValueChange = { measurement = it },
                        label = { Text("Measurement") },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter the Unit") },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Add button
                    Button(
                        onClick = {
                            if (name.isNotEmpty() && measurement.isNotEmpty()) {

                                viewModel.addIngredient(
                                    tempKey = tempKey,
                                    name=name,
                                    category = selectedOption,
                                    measurement=measurement
                                )
                                showBottomSheet.value = false
                                // 3️⃣ Open next sheet
                                showNextSheet.value = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Add",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

