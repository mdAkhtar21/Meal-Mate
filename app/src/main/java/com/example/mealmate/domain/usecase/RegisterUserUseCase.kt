package com.example.mealmate.domain.usecase

import com.example.mealmate.domain.model.User
import com.example.mealmate.domain.repository.UserRepository

class RegisterUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        repository.registerUser(user)
    }
}