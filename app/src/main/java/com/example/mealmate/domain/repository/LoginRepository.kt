package com.example.mealmate.domain.repository

import com.example.mealmate.data.local.Auth.UserEntity
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(email: String, password: String): UserEntity?
    fun isUserLoggedIn(): Flow<Boolean>
    suspend fun logout()
    fun getSavedUser(): Flow<Triple<Long,String?, String?>>
}
