package com.example.mealmate.presentation.register

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mealmate.R
import com.example.mealmate.presentation.common.CustomButton
import com.example.mealmate.presentation.common.CustomTextField
import com.example.mealmate.navigation.Screen
import com.example.mealmate.presentation.common.AppBar

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel= hiltViewModel(),
    navController:NavHostController
){
    var email by remember {  mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var currentPassword by remember { mutableStateOf("") }
    val uiState = viewModel.uiState.value

    val context= LocalContext.current

    Scaffold(
        topBar = {
            AppBar(
                topBarHeader = "Register",
                showBackButton = true,
                onBackClick = {
                    navController.popBackStack()
                },
            )

        }
    ){innerpadding->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerpadding)
            .padding(top = 20.dp, start = 10.dp, end = 10.dp)) {
            Text(
                text = stringResource(id = R.string.register),
                fontSize = 25.sp,
                fontWeight = FontWeight.W400,
                color = if(isSystemInDarkTheme()) Color.White else Color.Black,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.create_account),
                fontSize = 15.sp,
                color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomTextField(
                value = username,
                onValueChange ={
                    username=it
                },
                keyboardType = KeyboardType.Text,
                isPasswordTextfield = false,
                isSingleLine = true,
                imageVector = Icons.Default.Person,
                hint = R.string.username_hint
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomTextField(
                value = email,
                onValueChange ={
                    email=it
                },
                keyboardType = KeyboardType.Email,
                isPasswordTextfield = false,
                isSingleLine = true,
                imageVector = Icons.Default.Email,
                hint = R.string.email_hint
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomTextField(
                value = password,
                onValueChange ={
                    password=it
                },
                keyboardType = KeyboardType.Password,
                isPasswordTextfield = true,
                isSingleLine = true,
                imageVector = Icons.Default.Lock,
                hint = R.string.password_hint
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomTextField(
                value = currentPassword,
                onValueChange ={
                    currentPassword=it
                },
                keyboardType = KeyboardType.Password,
                isPasswordTextfield = true,
                isSingleLine = true,
                imageVector = Icons.Default.Lock,
                hint = R.string.confirm_password_hint
            )
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(id = R.string.login_content),
                fontSize = 15.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(20.dp))

            when(uiState){
                is UiState.Loading->{
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                is UiState.Error -> {
                    Text(
                        text = uiState.message,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                is UiState.Success->{
                    LaunchedEffect(Unit) {
                        navController.navigate(Screen.Home.route)
                        viewModel.resetState()
                    }
                }
                else -> {
                    CustomButton(
                        text = "Register",
                        onClick = {
                            when {
                                email.isEmpty() || username.isEmpty() || password.isEmpty() || currentPassword.isEmpty() -> {
                                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                                }
                                !AuthValidator.isEmailValid(email) -> {
                                    Toast.makeText(context, "Invalid email format", Toast.LENGTH_SHORT).show()
                                }
                                !AuthValidator.isPasswordValid(password) -> {
                                    Toast.makeText(
                                        context,
                                        "Password must contain:\n- 8+ characters\n- 1 Capital Letter\n- 1 Number\n- 1 Special Character",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                password != currentPassword -> {
                                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    viewModel.register(email, username, password)
                                }
                            }
                        },
                        bg = Color.Blue,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}