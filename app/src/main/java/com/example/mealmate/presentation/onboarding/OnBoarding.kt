package com.example.mealmate.presentation.onboarding

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealmate.navigation.Screen
import com.example.mealmate.presentation.Login.LoginViewModel
import com.example.mealmate.presentation.register.UiState

@Composable
fun OnBoarding(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {

        when (isLoggedIn) {

            null -> {  // STILL LOADING
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(16.dp))
                    Text("Checking login…", color = Color.Gray)
                }
            }

            true -> { // USER LOGGED-IN
                LaunchedEffect(true) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.onboarding.route) { inclusive = true }
                    }
                }
            }

            false -> { // NOT LOGGED-IN
                OnboardingContent(navController)
            }
        }
    }
}
