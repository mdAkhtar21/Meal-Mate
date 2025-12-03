package com.example.mealmate.presentation.common

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mealmate.R
import com.example.mealmate.navigation.Screen

data class BottomNavItem(
    val label: String,
    val icon: Painter
)



@Composable
fun BottomBar(
    selectedBottomItem: Int,
    onItemSelected: (Int) -> Unit,
    navController: NavController
) {
    val bottomNavItems = listOf(
        BottomNavItem("Home", painterResource(id = R.drawable.home)),
        BottomNavItem("Plan", painterResource(id = R.drawable.calendar)),
        BottomNavItem("List", painterResource(id = R.drawable.clipboard))
    )

    NavigationBar {
        bottomNavItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedBottomItem == index,
                onClick = {
                    onItemSelected(index)
                    // Navigate if needed
                    when (item.label) {
                        "Home" -> navController.navigate(Screen.Home.route)
                        "Plan" -> navController.navigate(Screen.PlanScreen.route)
                        "List" -> navController.navigate(Screen.ListScreen.route)
                    }
                },
                icon = { Icon(painter = item.icon, contentDescription = item.label, modifier = Modifier.size(20.dp)) },
                label = { Text(item.label) }
            )
        }
    }
}
