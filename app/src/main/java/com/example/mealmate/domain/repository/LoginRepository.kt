package com.example.mealmate.domain.repository

interface LoginRepository {
    suspend fun login(email: String, password: String): Boolean
}