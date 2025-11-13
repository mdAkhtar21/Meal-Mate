package com.example.mealmate.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.mealmate.navigation.Screen

data class BottomNavItem(
    val label: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("HomeScreen", Icons.Default.Home),
    BottomNavItem("PlanScreen", Icons.Default.Search),
    BottomNavItem("ListScreen", Icons.Default.AccountCircle)
)

@Composable
fun BottomBar(
    selectedBottomItem: Int,
    onItemSelected: (Int) -> Unit,
    navController: NavController
) {
    NavigationBar {
        bottomNavItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedBottomItem == index,
                onClick = {
                    onItemSelected(index) // update state in parent
                    // Navigate if needed
                    when (item.label) {
                        "HomeScreen" -> navController.navigate(Screen.Home.route)
                        "PlanScreen" -> navController.navigate(Screen.PlanScreen.route)
                        "ListScreen" -> navController.navigate(Screen.ListScreen.route)
                    }
                },
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
