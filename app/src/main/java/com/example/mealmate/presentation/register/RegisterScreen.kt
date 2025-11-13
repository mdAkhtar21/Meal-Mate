package com.example.mealmate.presentation.register

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 20.dp, start = 10.dp, end = 10.dp)) {


        AppBar(
            topBarHeader = "Register",
            showBackButton = true,
            onBackClick = {
                navController.popBackStack()
            },
        )



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

        CustomButton(
            text ="Login",
            onClick = {
                if(password==currentPassword){
                    viewModel.register(email,username,password)
                    navController.navigate(Screen.Home.route)
                }
            },
            bg = Color.Blue
        )
    }
}