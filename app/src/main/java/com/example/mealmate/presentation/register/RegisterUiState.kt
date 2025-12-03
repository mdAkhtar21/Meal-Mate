package com.example.mealmate.presentation.register

sealed class UiState<out T> {
    object Idle : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T? = null) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}