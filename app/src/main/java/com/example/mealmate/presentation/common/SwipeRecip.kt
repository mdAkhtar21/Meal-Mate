package com.example.mealmate.presentation.common

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mealmate.R
import com.example.mealmate.presentation.home.myrecipies.RecipeWithIngredientCount
import me.saket.swipe.SwipeableActionsBox


@Composable
fun SwipeRecipe(
    item: RecipeWithIngredientCount,
    onClick:(Long)->Unit
) {
    SwipeableActionsBox {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(start = 10.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .clickable { onClick(item.recipe.recipeId) },
            verticalAlignment = Alignment.CenterVertically,

        ) {

            if(item.recipe.recipeImage!=null){
                val bitmap=BitmapFactory.decodeByteArray(
                    item.recipe.recipeImage,0,item.recipe.recipeImage!!.size
                )
                Image(
                    bitmap=bitmap.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(120.dp).clip(RoundedCornerShape(10.dp))
                )
            }else{
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp), // optional spacing
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.recipe.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${item.ingredientCount} of ingredient",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}