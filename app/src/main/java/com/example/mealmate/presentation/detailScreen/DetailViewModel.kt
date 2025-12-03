package com.example.mealmate.presentation.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.data.Mapper.toEntity
import com.example.mealmate.domain.model.FullRecipeDetailDomain
import com.example.mealmate.domain.model.RecipeDetail
import com.example.mealmate.domain.usecase.RecipeUsecase.GetRecipeDetailUseCase
import com.example.mealmate.presentation.home.foryouscreen.StaticRecipeStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase,
) :ViewModel(){

    private val _recipeDetail=MutableStateFlow<FullRecipeDetailDomain?>(null)
    val recipeDetail:MutableStateFlow<FullRecipeDetailDomain?> = _recipeDetail

    fun loadRecipeDetails(recipeId: Long) {
        viewModelScope.launch {
            val details = getRecipeDetailUseCase(recipeId)
            if (details != null) {
                _recipeDetail.value = details
            }
            val staticData = StaticRecipeStore.staticRecipes.find { it.recipe.recipeId == recipeId }

            if (staticData != null) {
                _recipeDetail.value = convertStaticToFull(staticData)
            }
        }
    }
}

private fun convertStaticToFull(data: RecipeDetail): FullRecipeDetailDomain {
    return FullRecipeDetailDomain(
        recipe = data.recipe,
        ingredients = data.ingredients,
        instructions = data.instructions,
        sources = data.sources
    )
}





