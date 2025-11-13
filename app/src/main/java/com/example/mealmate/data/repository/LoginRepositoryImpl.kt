package com.example.mealmate.data.repository

import com.example.mealmate.data.datastore.UserPreferences
import com.example.mealmate.data.local.UserDao
import com.example.mealmate.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val userDao: UserDao,
    private val userPreferences: UserPreferences,
) :LoginRepository{
    override suspend fun login(email: String, password: String): Boolean {
        val userEntity= userDao.login(email,password)
        return if(userEntity!=null){
            userPreferences.saveUser(userEntity.email,userEntity.username)
            true
        }else{
            false
        }
    }

}