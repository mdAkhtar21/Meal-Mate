package com.example.mealmate.presentation.register

import androidx.compose.runtime.mutableStateOf
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

    var uiState= mutableStateOf<UiState<Unit>>(UiState.Idle)
        private set

    fun register(email: String, username: String, password: String) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                val user = User(email = email, username = username, password = password)
                registerUserUseCase(user)
                uiState.value=UiState.Success()
            }
            catch (e:Exception){
                uiState.value=UiState.Error(e.message?:"Registration failed")
            }

        }
    }
    fun resetState() {
        uiState.value = UiState.Idle
    }
}
