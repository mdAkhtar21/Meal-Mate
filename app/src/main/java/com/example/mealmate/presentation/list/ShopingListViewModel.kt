package com.example.mealmate.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.Ingredient
import com.example.mealmate.domain.usecase.IngredientUseCase.GetIngredientsForRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val getIngredientsForRecipeUseCase: GetIngredientsForRecipeUseCase
) : ViewModel() {

    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredients: StateFlow<List<Ingredient>> = _ingredients

    // ✅ Load ingredients for multiple recipes at once
    fun loadIngredientsForRecipes(recipeIds: List<Long>) {
        viewModelScope.launch {
            val allIngredients = recipeIds.distinct().flatMap { recipeId ->
                getIngredientsForRecipeUseCase.getByRecipeId(recipeId)
            }
            _ingredients.value = allIngredients
        }
    }
}
