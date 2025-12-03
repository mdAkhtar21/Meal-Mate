package com.example.mealmate.navigation

import MessageScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mealmate.presentation.home.addrecipie.AddRecipeScreen

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "auth_graph"
    ) {
        homeNavGraph(navController)
        authNavGraph(navController)
        planNavGraph(navController)
        listNavGraph(navController)

        composable(Screen.MessageScreen.route) {
            MessageScreen(navController = navController)
        }
        composable(Screen.AddRecipeScreen.route) {
            AddRecipeScreen(navController=navController)
        }
    }
}