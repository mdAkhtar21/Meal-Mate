package com.example.mealmate.domain.repository

import com.example.mealmate.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun registerUser(user: User)
    fun getCurrentUser(): Flow<User?>
}