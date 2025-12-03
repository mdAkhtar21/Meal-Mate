package com.example.mealmate.navigation

import kotlin.math.round

sealed class Screen(val route:String) {
    object Login : Screen(route = "login_Screen")
    object onboarding:Screen(route="on_boarding")
    object onBoardingComponent:Screen(route = "on_boarding_component")
    object SignUp : Screen(route = "signUp_Screen")
    object Home : Screen(route = "home")
    object MyRecipie : Screen(route = "myrecipies")
    object ListScreen : Screen(route = "list_screen")
    object PlanScreen : Screen(route = "plan_screen")
    object AddRecipeScreen : Screen(route = "add_recipe")
    object MyRecipieScreen : Screen(route = "my_recipes")
    object ForYouScreen:Screen(route = "for_you_screen")
    object DetailScreen : Screen("detail_screen/{recipeId}") {
        fun passId(id: Long) = "detail_screen/$id"
    }
    object MessageScreen : Screen("message_screen")
    object ProfileScreen:Screen("profile_screen")

}