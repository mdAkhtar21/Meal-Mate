package com.example.mealmate.domain.model

data class User(
    val id: Int = 0,
    val email: String,
    val username: String,
    val password: String
)