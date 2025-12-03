package com.example.mealmate.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealmate.R
import com.example.mealmate.domain.model.ShoppingListItem
import com.example.mealmate.navigation.Screen
import com.example.mealmate.presentation.common.AppBar
import com.example.mealmate.presentation.common.BottomBar
import com.example.mealmate.presentation.common.swipeListItem
import com.example.mealmate.presentation.list.EditList.EditItemShoppingList
import com.example.mealmate.presentation.list.addList.AddListItemBottomSheet
import kotlinx.coroutines.launch

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
    val showSnackbar = remember { SnackbarHostState() }



    Scaffold(
        snackbarHost = { SnackbarHost(hostState = showSnackbar) },
        topBar = {
            AppBar(
                topBarHeader = "List",
                onBackClick = { navController.popBackStack() },
                showBackButton = true,
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            onClick = {
                                scope.launch {
                                    if (shoppingListData.isEmpty()) {
                                        showSnackbar.showSnackbar("No Ingredent")
                                    } else {
                                        navController.navigate(Screen.MessageScreen.route)
                                    }
                                }
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
                        Box{
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
                                        viewModel.deleteAllShoppingList()
                                        menuExpand=false
                                    }
                                )
                            }
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

        if (shoppingListData.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.file),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(80.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "Empty List plan",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        "Add a new Entry and it will show up here",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black.copy(alpha = 0.9f),
                    )
                }
            }

            return@Scaffold
        }
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
