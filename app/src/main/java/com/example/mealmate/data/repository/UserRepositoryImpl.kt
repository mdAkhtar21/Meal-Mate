package com.example.mealmate.data.repository

import com.example.mealmate.data.datastore.UserPreferences
import com.example.mealmate.data.local.UserDao
import com.example.mealmate.data.local.UserEntity
import com.example.mealmate.domain.model.User
import com.example.mealmate.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userPreferences: UserPreferences
) : UserRepository {

    override suspend fun registerUser(user: User) {
        val entity = UserEntity(
            email = user.email,
            username = user.username,
            password = user.password
        )
        userDao.insertUser(entity)
        userPreferences.saveUser(user.email, user.username)
    }

    override fun getCurrentUser(): Flow<User?> {
        return userPreferences.getUser().map { (email, username) ->
            if (email != null && username != null) {
                User(email = email, username = username, password = "")
            } else null
        }
    }
}