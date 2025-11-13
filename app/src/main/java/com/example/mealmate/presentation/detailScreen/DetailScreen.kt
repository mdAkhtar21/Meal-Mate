package com.example.mealmate.presentation.detailScreen

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mealmate.R
import com.example.mealmate.presentation.common.AppBar
import com.example.mealmate.presentation.common.DetailAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    recipeId: Long?,
    navController: NavHostController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    LaunchedEffect(recipeId) {
        recipeId?.let { viewModel.loadRecipeDetails(it) }
    }
    val recipeDetail=viewModel.recipeDetail.collectAsState().value
    val recipeTabs = listOf("Ingredient", "Instruction")
    var selectedIndex by remember { mutableStateOf(0) }
    val showMealAddPlanSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val scope = rememberCoroutineScope()


    Scaffold(
        topBar = {
            DetailAppBar(
                name = recipeDetail?.recipe?.title ?: "Unknown Recipe",
                onClick = { navController.popBackStack() },
                ingredeintNumber = recipeDetail?.ingredients?.count { it.recipeId == recipeId } ?: 0
            )
        }
    ) {paddingValue->

        Column(
            modifier = Modifier
                .padding(paddingValue)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))


            val imageBytes = recipeDetail?.recipe?.recipeImage

            if (imageBytes != null) {
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Recipe Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Recipe Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = recipeDetail?.recipe?.title?: "Unknown Title",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.W400,
                fontSize = 35.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                showMealAddPlanSheet.value=true
            }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add to Plan",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Add on Plan",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
            if (showMealAddPlanSheet.value) {
                AddToMealPlan(
                    recipeId = recipeId,
                    showMealAddPlanSheet = showMealAddPlanSheet,
                    sheetState = sheetState,
                    scope = scope
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color.Gray.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // --- Category Section ---
            Column(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = recipeDetail?.recipe?.category ?: "Unknown Category",
                    style = MaterialTheme.typography.titleMedium,
                )


                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.cutlery),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${recipeDetail?.recipe?.category ?: "Unknown Category"}",
                            style = MaterialTheme.typography.titleMedium
                        )

                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.world),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = recipeDetail?.recipe?.style ?: "Unknown Style",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    val mealTypes = recipeDetail?.recipe?.mealType ?: emptyList()

                    items(mealTypes.size) { index ->
                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .height(35.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.Black.copy(alpha = 0.7f),
                                    shape = RoundedCornerShape(6.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = mealTypes[index],
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.W500,
                                color = Color.Black.copy(alpha = 0.7f),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color.Gray.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Tabs
            TabRow(selectedTabIndex = selectedIndex) {
                recipeTabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        text = { Text(text = title) }
                    )
                }
            }
            when(selectedIndex){
                0->IngredeintTabScreen(recipeId,viewModel)
                1->InstructionTabScreen(recipeId,viewModel)
            }
        }
    }

}
