package com.example.mealmate.presentation.detailScreen

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mealmate.data.local.AddRecipe.IngredientTableEntity
import com.example.mealmate.domain.model.Ingredient


@Preview(showBackground = true)
@Composable
fun IngredeintTabScreen(
    recipeId:Long?,
    viewModel: DetailViewModel= hiltViewModel()
) {
    val recipeDetail = viewModel.recipeDetail.collectAsState().value
    val ingredients = recipeDetail?.ingredients?.filter { it.recipeId==recipeId }?: emptyList()

    Column(modifier = Modifier.fillMaxSize().padding(top = 20.dp, start = 10.dp, end = 10.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Color.Black.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "${recipeDetail?.recipe?.serving} serving",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black.copy(alpha = 0.7f)
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Color.Black.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "${recipeDetail?.recipe?.preprationTime} prepration Time",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black.copy(alpha = 0.7f)
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(ingredients.size) {index->
                ingredeintlistItem(ingredients[index])
            }
        }
    }
}

@Composable
fun ingredeintlistItem(ingredient: IngredientTableEntity){
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
}