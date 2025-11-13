package com.example.mealmate.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mealmate.domain.model.ShoppingListItem
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun swipeListItem(
    item:ShoppingListItem,
    onCheckChange:(ShoppingListItem)->Unit,
    onDelete: (Long) -> Unit,
    onClick:(Long)->Unit
) {
    val deleteAction = SwipeAction(
        icon = {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        },
        background = Color.Red,
        onSwipe = { onDelete(item.id) }
    )

     SwipeableActionsBox (
         endActions = listOf(deleteAction)
     ){
         Row(
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(horizontal = 16.dp, vertical = 10.dp),
             verticalAlignment = Alignment.CenterVertically
         ) {
             Text(
                 text = item.ingredientName,
                 fontWeight = FontWeight.W600,
                 style = MaterialTheme.typography.bodyLarge,
                 modifier = Modifier.weight(1f)
                     .clickable { onClick(item.id)}
             )
             Checkbox(
                 checked = item.isChecked,
                 onCheckedChange = { checked ->
                     onCheckChange(item.copy(isChecked = checked))
                 }
             )
         }
     }
}