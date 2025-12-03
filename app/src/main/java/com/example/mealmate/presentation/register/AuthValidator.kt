package com.example.mealmate.presentation.register


object AuthValidator {

    fun isEmailValid(email: String): Boolean {
        return email.contains("@") && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        val passwordRegex =
            "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#\$%^&+=!]).{8,}$".toRegex()

        // Requirements:
        // - At least 8 characters
        // - At least 1 uppercase letter
        // - At least 1 number
        // - At least 1 special character (@#$%^&+=!)
        return passwordRegex.matches(password)
    }
}