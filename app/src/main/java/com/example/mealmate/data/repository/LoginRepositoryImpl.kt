package com.example.mealmate.data.repository

import com.example.mealmate.data.datastore.UserPreferences
import com.example.mealmate.data.local.Auth.UserDao
import com.example.mealmate.data.local.Auth.UserEntity
import com.example.mealmate.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginRepositoryImpl(
    private val userDao: UserDao,
    private val userPreferences: UserPreferences,
) : LoginRepository {

    override suspend fun login(email: String, password: String): UserEntity? {
        val user = userDao.login(email, password)
        if (user != null) {
            userPreferences.saveUser(user.id,user.email, user.username)
        }
        return user
    }
    override fun isUserLoggedIn(): Flow<Boolean> {
        return userPreferences.isUserLoggedIn()
    }

    override suspend fun logout() {
        userPreferences.clearUser()
    }

    override fun getSavedUser(): Flow<Triple<Long,String?, String?>> {
        return userPreferences.getUser()
    }
}
