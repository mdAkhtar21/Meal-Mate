package com.example.mealmate.domain.model

data class User(
    val id: Long = 0L,
    val email: String,
    val username: String,
    val password: String
)