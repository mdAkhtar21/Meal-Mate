package com.example.mealmate.domain.usecase

import com.example.mealmate.data.local.UserDao
import com.example.mealmate.data.local.UserEntity
import com.example.mealmate.domain.model.User
import com.example.mealmate.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String): Boolean {
        return repository.login(email, password)!=null
    }
}
