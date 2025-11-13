package com.example.mealmate.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomFloatActionButton(
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = Color.Gray,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.size(50.dp).background(color = Color.Transparent)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun previewComposble() {
    CustomFloatActionButton()
}
