package com.example.mealmate.presentation.list

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mealmate.domain.model.ShoppingListItem
import com.example.mealmate.presentation.detailScreen.DetailViewModel
import com.example.mealmate.presentation.home.addrecipie.AddRecipeViewModel
import kotlinx.coroutines.CoroutineScope


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditItemShoppingList(
    sheetState: SheetState,
    showBottomSheet: MutableState<Boolean>,
    scope: CoroutineScope,
    viewModel: ListViewModel = hiltViewModel(),
    viewmodelrecipe:AddRecipeViewModel= hiltViewModel(),
    itemToEdit: ShoppingListItem,
)  {
    var expanded by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(itemToEdit.ingredientName) }
    var quantity by remember { mutableStateOf(itemToEdit.quantity) }
    var comment by remember { mutableStateOf(itemToEdit.comment ?: "") }
    var selectedCategory by remember { mutableStateOf(itemToEdit.categoryName) }

    val recipesList by viewmodelrecipe.recipes.collectAsState()
    val selectedRecipe = recipesList.find { it.recipeId == itemToEdit.recipeId }



    val options = listOf(
        "Baking", "Bread and Bakery", "Condiments", "Dairy and Eggs",
        "Frozen", "Fruits and Vegetables", "Herbs and Spices",
        "Household", "Meals and Safe Food", "Pasta, Rice and Beans"
    )

    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet.value = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Text(
                    text = "Edit Item",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                /** Category Dropdown */
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Ingredient Category") },
                        trailingIcon = {
                            Icon(
                                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedCategory = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                /** Name Field */
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Item Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                /** Quantity Field */
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantity") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    label = { Text("Comment (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    selectedRecipe?.recipeImage?.let { byteArray ->
                        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(60.dp))
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = selectedRecipe?.title ?: "Unknown Recipe",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                /** Add Button */
                Button(onClick = {
                    viewModel.updateShoppingListItem(
                        itemToEdit.copy(
                            ingredientName = name,
                            categoryName = selectedCategory,
                            quantity = quantity,
                            comment = comment
                        )
                    )
                    showBottomSheet.value = false
                },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Update",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}