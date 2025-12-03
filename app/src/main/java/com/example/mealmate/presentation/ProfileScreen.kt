package com.example.mealmate.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealmate.R
import com.example.mealmate.navigation.Screen
import com.example.mealmate.presentation.Login.LoginViewModel
import com.example.mealmate.presentation.common.AppBar

@Preview(showBackground = true)
@Composable
fun ProfileScreen(
    viewModel:LoginViewModel= hiltViewModel(),
    navController: NavController
) {
    val login by viewModel.login.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            AppBar(
                topBarHeader = "Profile",
                showBackButton = true,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // User info
                Column {
                    Text(
                        text = "${login.email}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${login.name}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }

                Button(
                    onClick = {
                        showDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.Red
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    modifier = Modifier.width(130.dp).height(40.dp),
                    shape = RoundedCornerShape(size = 5.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.exit),
                        contentDescription = "Logout",
                        tint = Color.Red,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Logout",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            if (showDialog) {
                SimpleAlertDialog(showDialogState = remember { mutableStateOf(showDialog) }) { confirmed ->
                    showDialog = false
                    if (confirmed) {
                        viewModel.logout {
                            navController.navigate(Screen.onboarding.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SimpleAlertDialog(
    showDialogState: MutableState<Boolean>,
    onDismiss: (Boolean) -> Unit,
) {
    if (showDialogState.value) {
        AlertDialog(
            onDismissRequest = {
                showDialogState.value = false
                onDismiss(false)
            },
            title = { Text(text = "Are you logging out?") },
            text = { Text("You can always log back in at any time") },
            confirmButton = {
                TextButton(onClick = {
                    showDialogState.value = false
                    onDismiss(true)
                }) {
                    Text("Ok")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialogState.value = false
                    onDismiss(false)
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}
