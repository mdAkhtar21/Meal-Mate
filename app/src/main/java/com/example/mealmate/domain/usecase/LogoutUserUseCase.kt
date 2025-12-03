package com.example.mealmate.domain.usecase

import com.example.mealmate.domain.repository.LoginRepository
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke() {
        loginRepository.logout()
    }
}