package com.example.mealmate.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mealmate.presentation.plan.PlanScreen

fun NavGraphBuilder.planNavGraph(navController: androidx.navigation.NavHostController) {
    navigation(
        startDestination = Screen.PlanScreen.route,
        route = "plan_graph"
    ) {
        composable(Screen.PlanScreen.route) {
            PlanScreen(navController)
        }
    }
}