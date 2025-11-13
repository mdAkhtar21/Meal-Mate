package com.example.mealmate.navigation

import MessageScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mealmate.presentation.Login.LoginScreen
import com.example.mealmate.presentation.detailScreen.DetailScreen
import com.example.mealmate.presentation.home.HomeScreen
import com.example.mealmate.presentation.home.addrecipie.AddRecipeScreen
import com.example.mealmate.presentation.home.foryouscreen.ForYouScreen
import com.example.mealmate.presentation.home.myrecipies.MyRecipie
import com.example.mealmate.presentation.list.ListScreen
import com.example.mealmate.presentation.plan.PlanScreen
import com.example.mealmate.presentation.register.RegisterScreen
import kotlin.reflect.typeOf

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "home_graph"
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