package com.example.mealmate.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealmate.R
import com.example.mealmate.presentation.common.CustomButton

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnBoarding() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.meal_mate),
                fontSize = 40.sp,
                fontWeight = FontWeight.W400
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.about_meal),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomButton(
                text = "Login",
                onClick = {},
                bg = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(15.dp))
            CustomButton(
                text = "Create Account",
                onClick = {},
                bg = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "By using Meal Mate, you agree to our "
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(text = "Term", fontWeight = FontWeight.Bold, color = Color.Gray)
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "and")
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Privacy Policy", fontWeight = FontWeight.Bold, color = Color.Gray)
            }
        }
    }
}
