package com.example.mealmate.presentation.home.addrecipie


import android.app.TimePickerDialog
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.mealmate.R
import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity
import com.example.mealmate.navigation.Screen
import com.example.mealmate.presentation.common.AppBar
import com.example.mealmate.presentation.common.CustomSelectMealButton
import com.example.mealmate.presentation.common.CustomTextField2
import com.example.mealmate.presentation.home.addrecipie.RecipeSource.RecipeSourceBottomSheet
import com.example.mealmate.presentation.home.addrecipie.ingredient.IngredientBottomSheet
import com.example.mealmate.presentation.home.addrecipie.ingredient.IngredientViewModel
import com.example.mealmate.presentation.home.addrecipie.instruction.InstructionBottomSheet
import com.example.mealmate.presentation.home.addrecipie.instruction.InstructionViewModel
import java.io.ByteArrayOutputStream
import java.util.UUID


@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AddRecipeScreen(
    viewModel: AddRecipeViewModel = hiltViewModel(),
    navController: NavController
) {
    val selectedMealTypes by viewModel.selectedMealTypes.collectAsState()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var style by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var preprationTime by remember { mutableStateOf("0") }
    var cookingTime by remember { mutableStateOf("0") }

    var selectedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // ✅ Launcher to pick image from gallery
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            selectedImageBitmap = ImageDecoder.decodeBitmap(source)
        }
    }

    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val PreprationtimePickerDialog = TimePickerDialog(
        context,
        { _, selectHour, selectedMinute ->
            preprationTime = String.format("%02d:%02d", selectHour, selectedMinute)
        },
        hour,
        minute,
        true
    )
    val CookingtimePickerDialog = TimePickerDialog(
        context,
        { _, selectHour, selectedMinute ->
            cookingTime = String.format("%02d:%02d", selectHour, selectedMinute)
        },
        hour,
        minute,
        true
    )
    var servingCount by remember { mutableStateOf(0) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val scope = rememberCoroutineScope()
    val showIngredientSheet = remember { mutableStateOf(false) }
    val showInstructionSheet = remember { mutableStateOf(false) }
    val showRecipeBottomSheet = remember { mutableStateOf(false) }

    val tempKey = remember  { System.currentTimeMillis() }
    val ingredientViewModel: IngredientViewModel = hiltViewModel()
    val ingredients by ingredientViewModel.ingredient.collectAsState()
    val instructionViewModel: InstructionViewModel = hiltViewModel()
    val instruction by instructionViewModel.instructions.collectAsState()

    Scaffold(
        topBar = {
            AppBar(
                topBarHeader = "Add Recipe",
                showBackButton = true,
                onBackClick = { },
                actions = {
                    Button(
                        onClick = {
                            // create RecipeTableEntity with proper fields
                            val recipeEntity = RecipeTableEntity(
                                title = title,
                                description = description,
                                recipeImage = selectedImageBitmap?.let { bitmap ->
                                    val outputStream = ByteArrayOutputStream()
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                                    outputStream.toByteArray()
                                },
                                category = category,
                                style = style,
                                mealType = selectedMealTypes,
                                serving = servingCount,
                                preprationTime = preprationTime,
                                cookingTime = cookingTime
                            )

                            viewModel.insertRecipe(recipeEntity, tempKey)
                            navController.navigate(Screen.Home.route + "?selectedTab=1") {
                                popUpTo(Screen.Home.route) { inclusive = false }
                            }

                        }
                    )  {
                        Text(
                            text = "Save",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            // Title
            Text(
                text = "Title",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
            CustomTextField2(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                placeholder = "Butter Chicken",
                onValueChange = { title = it },
                keyboardType = KeyboardType.Text
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
            CustomTextField2(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                placeholder = "About your Recipe",
                onValueChange = { description=it },
                keyboardType = KeyboardType.Text
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Image and Category / Style
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Recipe Image",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .drawBehind {
                                val strokeWidth = 2.dp.toPx()
                                val dashLength = 10.dp.toPx()
                                val gapLength = 10.dp.toPx()
                                val pathEffect =
                                    PathEffect.dashPathEffect(floatArrayOf(dashLength, gapLength), 0f)
                                drawRect(
                                    color = Color.Green,
                                    size = Size(size.width, size.height),
                                    style = Stroke(width = strokeWidth, pathEffect = pathEffect)
                                )
                            }.clickable {
                                imagePickerLauncher.launch("image/*")

                            }
                    ){
                        if (selectedImageBitmap  != null) {
                            Image(
                                painter = rememberAsyncImagePainter(selectedImageBitmap ),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(10.dp))
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Category",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CustomTextField2(
                        modifier = Modifier.fillMaxWidth(),
                        value = category,
                        placeholder = "e.g Chicken",
                        onValueChange = { category=it },
                        keyboardType = KeyboardType.Text
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Style",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CustomTextField2(
                        modifier = Modifier.fillMaxWidth(),
                        value = style,
                        placeholder = "e.g Italian",
                        onValueChange = { style=it },
                        keyboardType = KeyboardType.Text
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Meal Types
            Text(
                text = "Meal Types",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Select one or more",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomSelectMealButton(
                    modifier = Modifier.weight(1f),
                    text = "Breakfast",
                    isClicked = selectedMealTypes.contains("Breakfast"),
                    onClick = {
                        viewModel.toggleMealType("Breakfast")
                    }
                )
                CustomSelectMealButton(
                    modifier = Modifier.weight(1f),
                    text = "Lunch",
                    isClicked = selectedMealTypes.contains("Lunch"),
                    onClick = {
                        viewModel.toggleMealType("Lunch")
                    }
                )
                CustomSelectMealButton(
                    modifier = Modifier.weight(1f),
                    text = "Snacks",
                    isClicked = selectedMealTypes.contains("Snacks"),
                    onClick = {
                        viewModel.toggleMealType("Snacks")
                    }
                )
                CustomSelectMealButton(
                    modifier = Modifier.weight(1f),
                    text = "Dinner",
                    isClicked = selectedMealTypes.contains("Dinner"),
                    onClick = {
                        viewModel.toggleMealType("Dinner")
                    }
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Serving & Time
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Serving",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
                Text(
                    text = "How many people serve the meal",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$servingCount",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                    Row {
                        IconButton(
                            onClick = { servingCount++ }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Increase serving"
                            )
                        }
                        IconButton(
                            onClick = { if (servingCount > 0) servingCount-- }
                        ) {
                           Image(
                               painter = painterResource(
                                   id = R.drawable.minus,
                               ),
                               contentDescription=null,
                               modifier = Modifier.size(25.dp)
                           )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Preparation Time",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
                Text(
                    text = "Time required to prepare the meal",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$preprationTime min",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Button(onClick = { PreprationtimePickerDialog.show() }) {
                        Text(text = "Set Time")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Cooking Time",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
                Text(
                    text = "Time required to cook the meal",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$cookingTime min",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Button(onClick = { CookingtimePickerDialog.show() }) {
                        Text(text = "Set Time")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ingredient
            Text(
                text = "Ingredient",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                if(ingredients.size==0){
                    Text(
                        text = "There are no ingredients yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }else{
                    Text(
                        text = "There are ${ingredients.size} ingredients yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
                Button(onClick = { showIngredientSheet.value = true }) {
                    Text(text = "Edit")
                }
                IngredientBottomSheet(
                    tempKey = tempKey,
                    sheetState = sheetState,
                    scope = scope,
                    showIngredientSheet = showIngredientSheet,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Instruction
            Text(
                text = "Instruction",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(instruction.size==0){
                    Text(
                        text = "There are no instructions yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }else{
                    Text(
                        text = "There are ${instruction.size} instructions yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }

                Button(onClick = { showInstructionSheet.value = true }) {
                    Text(text = "Edit")
                }
                InstructionBottomSheet(
                    sheetState = sheetState,
                    scope = scope,
                    showBottomSheet = showInstructionSheet,
                    tempKey = tempKey,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Recipe Source
            Text(
                text = "Recipe Source",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { showRecipeBottomSheet.value = true }) {
                Text(text = "Edit")
            }
            RecipeSourceBottomSheet(
                sheetState = sheetState,
                scope = scope,
                showBottomSheet = showRecipeBottomSheet,
                tempKey = tempKey,
            )
        }

    }
}


