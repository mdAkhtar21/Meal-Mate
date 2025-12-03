package com.example.mealmate.domain.usecase

import com.example.mealmate.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(email: String, password: String) =
        loginRepository.login(email, password)


    // ADD THIS → Check login status
    fun isUserLoggedIn(): Flow<Boolean> {
        return loginRepository.isUserLoggedIn()
    }

    fun getLoggedInUser() = loginRepository.getSavedUser()
}
