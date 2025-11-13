package com.example.mealmate.presentation.Login

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel= hiltViewModel(),
    navController:NavHostController
) {

    val context= LocalContext.current
    // State variables for text fields
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        AppBar(
            topBarHeader = "SignIn",
            showBackButton = true,
            onBackClick = {
                navController.popBackStack()
            }
        )
        Text(
            text = stringResource(id = R.string.welcome), // use stringResource
            fontSize = 30.sp,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Login to your account")

        Spacer(modifier = Modifier.height(20.dp))

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            hint = R.string.email_hint, // You should have a string resource like "Enter your email"
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
        Text(text = stringResource(id = R.string.login_content), fontSize = 15.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(20.dp))
        CustomButton(
            text = "SignIn",
            onClick = {
                when {
                    email.isEmpty() || password.isEmpty() -> {
                        Toast.makeText(context, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        viewModel.login(email, password) { success ->
                            if (success) {
                                onLoginSuccess()
                            } else {
                                Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            },
            bg = Color.Blue
        )

//        errorMessage?.let{
//            Spacer(modifier = Modifier.height(12.dp))
//            Text(text = it, color = Color.Red, fontSize = 14.sp)
//        }
    }
}
