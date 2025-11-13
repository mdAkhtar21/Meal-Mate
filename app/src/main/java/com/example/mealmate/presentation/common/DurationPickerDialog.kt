package com.example.mealmate.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DurationPickerDialog(
    showDialog: MutableState<Boolean>,
    selectedTime: MutableState<String>
) {
    if (showDialog.value) {
        var hours by remember { mutableStateOf(0) }
        var minutes by remember { mutableStateOf(0) }

        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Set Preparation Time") },
            text = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    // Hours Picker
                    Column {
                        Text(text = "Hrs", fontSize = 16.sp)
                        LazyColumn(
                            modifier = Modifier.weight(1f)
                        ) {
                            items(6) { index ->
                                Text(
                                    text = index.toString(),
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .selectable(
                                            selected = index == hours,
                                            onClick = { hours = index }
                                        ),
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }

                    // Minutes Picker
                    Column {
                        Text(text = "Min", fontSize = 16.sp)
                        LazyColumn(
                            modifier = Modifier.weight(1f)
                        ) {
                            items(60) { index ->
                                Text(
                                    text = index.toString().padStart(2, '0'),
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .selectable(
                                            selected = index == minutes,
                                            onClick = { minutes = index }
                                        ),
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    selectedTime.value = String.format("%02d:%02d", hours, minutes)
                    showDialog.value = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DurationPickerDialogPreview() {
    val showDialog = remember { mutableStateOf(true) }
    val selectedTime = remember { mutableStateOf("00:00") }
    DurationPickerDialog(showDialog, selectedTime)
}
