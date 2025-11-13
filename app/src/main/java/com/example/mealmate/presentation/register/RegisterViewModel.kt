package com.example.mealmate.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.User
import com.example.mealmate.domain.usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    fun register(email: String, username: String, password: String) {
        viewModelScope.launch {
            val user = User(email = email, username = username, password = password)
            registerUserUseCase(user)
        }
    }
}
