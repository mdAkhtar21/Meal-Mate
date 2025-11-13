package com.example.mealmate.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealmate.domain.model.ShoppingListItem
import com.example.mealmate.navigation.Screen
import com.example.mealmate.presentation.common.AppBar
import com.example.mealmate.presentation.common.BottomBar
import com.example.mealmate.presentation.common.swipeListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navController: NavController,
    viewModel: ListViewModel = hiltViewModel()
) {
    var selectedBottomItem by remember { mutableStateOf(2) }
    val shoppingListData by viewModel.shoppingListItems.collectAsState()

    var menuExpand by remember { mutableStateOf(false) }


    val showBottomSheet = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    var selectedItem by remember { mutableStateOf<ShoppingListItem?>(null) }
    val showEditSheet = remember { mutableStateOf(false) }
    val addSheetState = rememberModalBottomSheetState(skipPartiallyExpanded =true)
    val editSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


    Scaffold(
        topBar = {
            AppBar(
                topBarHeader = "List",
                onBackClick = { navController.popBackStack() },
                showBackButton = true,
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            onClick = {
                                navController.navigate(Screen.MessageScreen.route)
                            },
                            modifier = Modifier.height(40.dp),
                            shape = RoundedCornerShape(
                                topStart = 20.dp,
                                bottomStart = 20.dp,
                                topEnd = 0.dp,
                                bottomEnd = 0.dp
                            ),
                            contentPadding = PaddingValues(start = 12.dp, end = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Send Message",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White,
                                fontWeight = FontWeight.W500
                            )
                        }
                        Spacer(modifier = Modifier.width(2.dp))
                        Button(
                            onClick = { menuExpand=true},
                            modifier = Modifier
                                .height(40.dp)
                                .width(40.dp),
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                bottomStart = 0.dp,
                                topEnd = 20.dp,
                                bottomEnd = 20.dp
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        DropdownMenu(
                            expanded = menuExpand,
                            onDismissRequest = {menuExpand=false},
                            modifier = Modifier.width(120.dp).background(Color.Gray)
                        ) {
                            DropdownMenuItem(
                                text = { Text("Add item") },

                                onClick = {
                                    menuExpand=false
                                    showBottomSheet.value = true
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Remove Items") },
                                onClick = {
                                    menuExpand=false
                                }
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(
                selectedBottomItem = selectedBottomItem,
                onItemSelected = { selectedBottomItem = it },
                navController = navController
            )
        }
    ) { innerPadding ->


        val groupList=shoppingListData.groupBy { it.categoryName }
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            groupList.forEach { (category, items) ->

                item {
                    Text(
                        text = category,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                items(items) { item ->
                    swipeListItem(
                        item = item,
                        onDelete = { viewModel.deleteShoppingListItem(item.id) },
                        onCheckChange = { updateItem ->
                            viewModel.updateShoppingListItem(updateItem)
                        },
                        onClick = { itemClicked ->
                            selectedItem = item
                            showEditSheet.value = true
                        }
                    )


                    HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                }
            }
        }

    }
    if (showBottomSheet.value){
        AddListItemBottomSheet(
            sheetState = addSheetState,
            showBottomSheet = showBottomSheet,
            scope = scope
        )
    }

    if (showEditSheet.value && selectedItem != null) {
        EditItemShoppingList(
            sheetState = editSheetState,
            showBottomSheet = showEditSheet,
            scope = scope,
            itemToEdit = selectedItem!!
        )
    }
}

