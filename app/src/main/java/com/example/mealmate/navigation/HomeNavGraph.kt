package com.example.mealmate.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mealmate.presentation.detailScreen.DetailScreen
import com.example.mealmate.presentation.home.HomeScreen
import com.example.mealmate.presentation.home.foryouscreen.ForYouScreen
import com.example.mealmate.presentation.home.myrecipies.MyRecipie

fun NavGraphBuilder.homeNavGraph(navController: androidx.navigation.NavHostController) {
    navigation(
        startDestination = Screen.Home.route,
        route = "home_graph"
    ) {
        composable(
            route = Screen.Home.route + "?selectedTab={selectedTab}",
            arguments = listOf(
                androidx.navigation.navArgument("selectedTab") {
                    type = androidx.navigation.NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val selectedTab = backStackEntry.arguments?.getInt("selectedTab") ?: 0
            HomeScreen(navController, selectedTab)
        }

        composable(Screen.ForYouScreen.route) {
            ForYouScreen(navController)
        }

        composable(Screen.MyRecipieScreen.route) {
            MyRecipie(navController)
        }

        composable(Screen.DetailScreen.route) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getLong("recipeId")
            DetailScreen(recipeId = recipeId, navController = navController)
        }
    }
}