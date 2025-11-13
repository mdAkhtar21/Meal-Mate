package com.example.mealmate.presentation.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.usecase.RecipeUsecase.FullRecipeDetail
import com.example.mealmate.domain.usecase.RecipeUsecase.GetRecipeDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase,
) :ViewModel(){

    private val _recipeDetail=MutableStateFlow<FullRecipeDetail?>(null)
    val recipeDetail:MutableStateFlow<FullRecipeDetail?> = _recipeDetail

    fun loadRecipeDetails(recipeId:Long){
        viewModelScope.launch {
            val details=getRecipeDetailUseCase(recipeId)
            println("DEBUG: loadRecipeDetails -> $details")
            recipeDetail.value=details

        }
    }
}