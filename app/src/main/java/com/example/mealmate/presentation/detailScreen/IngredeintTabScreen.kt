package com.example.mealmate.presentation.detailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mealmate.R
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
                painter = painterResource(id = R.drawable.soup),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "${recipeDetail?.recipe?.serving} serving",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black.copy(alpha = 0.7f)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.stopwatch),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))


            Text(
                text = "${recipeDetail?.recipe?.preprationTime} prepration Time",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Icon(
                painter = painterResource(id = R.drawable.pan),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "${recipeDetail?.recipe?.cookingTime} cooking Time",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black.copy(alpha = 0.7f)
            )
        }

        Spacer(modifier=Modifier.height(10.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color.Gray.copy(alpha = 0.5f)
        )
        Spacer(modifier=Modifier.height(10.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            ingredients.forEach { ingredient ->
                ingredeintlistItem(ingredient)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ingredeintlistItem(ingredient: Ingredient){
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