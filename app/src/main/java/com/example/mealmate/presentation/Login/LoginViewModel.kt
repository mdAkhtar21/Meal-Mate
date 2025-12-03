package com.example.mealmate.presentation.Login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.usecase.LoginUserUseCase
import com.example.mealmate.domain.usecase.LogoutUserUseCase
import com.example.mealmate.presentation.register.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginData(
    val userId:Long=0L,
    val name: String = "",
    val email: String = ""
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) : ViewModel() {

    private val _login = MutableStateFlow(LoginData())
    val login: StateFlow<LoginData> = _login

    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn

    var uiState= mutableStateOf<UiState<Unit>>(UiState.Idle)
        private set

    init {
        checkUserLogin()
        loadLoginUser()
    }

    private fun loadLoginUser() {
        viewModelScope.launch {
            loginUserUseCase.getLoggedInUser().collect { (id, name, email) ->
                _login.value = LoginData(
                    userId = id,
                    name = name ?: "",
                    email = email ?: ""
                )
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                val user = loginUserUseCase(email, password)
                if (user != null) {
                    _isLoggedIn.value = true
                    uiState.value = UiState.Success()
                } else {
                    uiState.value = UiState.Error("Invalid email or password")
                }
            } catch (exception: Exception) {
                uiState.value = UiState.Error(exception.message ?: "Login failed")
            }
        }
    }

    private fun checkUserLogin() {
        viewModelScope.launch {
            loginUserUseCase.isUserLoggedIn().collect { loggedIn ->
                _isLoggedIn.value = loggedIn
                uiState.value = UiState.Idle
            }
        }
    }

    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            logoutUserUseCase()
            _isLoggedIn.value = false
            onComplete()
        }
    }
    fun resetState() {
        uiState.value = UiState.Idle
    }

}
