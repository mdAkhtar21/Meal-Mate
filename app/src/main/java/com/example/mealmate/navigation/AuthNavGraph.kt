package com.example.mealmate.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mealmate.presentation.Login.LoginScreen
import com.example.mealmate.presentation.onboarding.OnBoarding
import com.example.mealmate.presentation.onboarding.OnboardingContent
import com.example.mealmate.presentation.register.RegisterScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.onboarding.route,
        route = "auth_graph"
    ) {
        composable(Screen.onboarding.route) {
            OnBoarding(navController=navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo("auth_graph") { inclusive = true }
                    }
                },
                navController = navController
            )
        }

        composable(Screen.SignUp.route) {
            RegisterScreen(navController = navController)
        }
    }
}