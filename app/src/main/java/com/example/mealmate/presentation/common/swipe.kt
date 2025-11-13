package com.example.mealmate.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealmate.domain.model.Ingredient
import com.example.mealmate.presentation.home.addrecipie.ingredient.IngredientViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun SwipeIngredientItem(
    ingredient: Ingredient,
    viewModel: IngredientViewModel,
    recipeId: Long,
    showCategory: Boolean = true
) {
    val deleteAction = SwipeAction(
        onSwipe = { viewModel.deleteIngredient(recipeId, ingredient.ingredientId) },
        icon = {
            Icon(
                imageVector = Icons.Default.Delete,
                modifier = Modifier.padding(16.dp),
                contentDescription = null,
                tint = Color.White
            )
        },
        background = Color.Red
    )


    SwipeableActionsBox(
        startActions = listOf(deleteAction),
        endActions = listOf(deleteAction)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (showCategory) {
                Text(
                    text = ingredient.category,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = ingredient.name,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Text(
                    text = "${ingredient.measurement}g",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color.LightGray
            )
        }
    }
}
