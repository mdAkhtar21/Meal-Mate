package com.example.mealmate.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mealmate.presentation.list.ListScreen


fun NavGraphBuilder.listNavGraph(navController: androidx.navigation.NavHostController) {
    navigation(
        startDestination = Screen.ListScreen.route,
        route = "list_graph"
    ) {
        composable(Screen.ListScreen.route) {
            ListScreen(navController)
        }
    }
}