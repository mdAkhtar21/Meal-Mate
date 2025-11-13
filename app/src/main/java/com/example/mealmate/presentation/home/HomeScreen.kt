package com.example.mealmate.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.mealmate.navigation.Screen
import com.example.mealmate.presentation.common.AppBar
import com.example.mealmate.presentation.common.BottomBar
import com.example.mealmate.presentation.common.CustomFloatActionButton
import com.example.mealmate.presentation.home.foryouscreen.ForYouScreen
import com.example.mealmate.presentation.home.myrecipies.MyRecipie

@Composable
fun HomeScreen(
    navController: NavController,
    defaultSelectedTab: Int = 0
) {
    val tabs = listOf("For You", "My Recipes")
    var selectedIndex by remember { mutableStateOf(defaultSelectedTab) }

    Scaffold(
        topBar = {
            AppBar(
                topBarHeader = "Home",
                showBackButton = false
            )
        },
        floatingActionButton = {
            CustomFloatActionButton(
                onClick = { navController.navigate(Screen.AddRecipeScreen.route) }
            )
        },
        bottomBar = {
            BottomBar(
                selectedBottomItem = 0,
                onItemSelected = {},
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TabRow(selectedTabIndex = selectedIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        text = { Text(title) }
                    )
                }
            }
            when (selectedIndex) {
                0 -> ForYouScreen(navController)
                1 -> MyRecipie(navController)
            }
        }
    }
}
