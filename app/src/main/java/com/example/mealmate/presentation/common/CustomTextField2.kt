package com.example.mealmate.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField2(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType
) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .height(50.dp).background(Color.Transparent),
        shape = RoundedCornerShape(size = 8.dp),
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 16.sp,
                color = Color.Gray
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.surfaceVariant
            } else {
                Gray
            },
            unfocusedContainerColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colorScheme.surface
            } else {
                Gray
            },
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Preview
@Composable
private fun customTextFieldPreview() {
    val name by remember { mutableStateOf("") }
    CustomTextField2(
        value = "Hello",
        placeholder = "Enter Name",
        onValueChange = {},
        keyboardType = KeyboardType.Text
    )
    
}
