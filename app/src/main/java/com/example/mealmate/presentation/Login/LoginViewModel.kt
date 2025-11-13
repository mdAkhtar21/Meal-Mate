package com.example.mealmate.presentation.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.User
import com.example.mealmate.domain.usecase.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    fun login(email: String, password: String,onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = loginUserUseCase(email, password)
            onResult(success)
        }
    }
}