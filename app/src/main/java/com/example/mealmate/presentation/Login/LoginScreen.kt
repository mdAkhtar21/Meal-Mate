package com.example.mealmate.presentation.Login

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mealmate.R
import com.example.mealmate.presentation.common.CustomButton
import com.example.mealmate.presentation.common.CustomTextField
import com.example.mealmate.presentation.common.AppBar
import com.example.mealmate.presentation.register.UiState

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState.value
    val context = LocalContext.current
    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.Success -> {
                onLoginSuccess()
                viewModel.resetState()
            }
            is UiState.Error -> {
                Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppBar(
                topBarHeader = "SignIn",
                showBackButton = true,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
                .padding(top = 20.dp, start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.welcome),
                fontSize = 30.sp,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Login to your account")
            Spacer(modifier = Modifier.height(20.dp))

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                hint = R.string.email_hint,
                imageVector = Icons.Default.Email
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomTextField(
                value = password,
                onValueChange = { password = it },
                hint = R.string.password_hint,
                imageVector = Icons.Default.Lock,
                isPasswordTextfield = true
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.login_content),
                fontSize = 15.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(20.dp))

            CustomButton(
                text = "SignIn",
                onClick = {
                    when {
                        email.isEmpty() || password.isEmpty() -> {
                            Toast.makeText(context, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                        }
                        else -> viewModel.login(email, password)
                    }
                },
                bg = Color.Blue
            )
        }

    }
}
