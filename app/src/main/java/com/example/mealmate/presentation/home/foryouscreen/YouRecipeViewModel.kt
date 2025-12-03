package com.example.mealmate.presentation.home.foryouscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.data.datastore.UserPreferences
import com.example.mealmate.domain.model.RecipeWithIngredientCount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YouRecipeViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<RecipeWithIngredientCount>>(emptyList())
    val recipes: StateFlow<List<RecipeWithIngredientCount>> = _recipes

    private val _userId = MutableStateFlow(0L)
    val userId: StateFlow<Long> = _userId

    init {
        viewModelScope.launch {
            val triple = userPreferences.getUser().first()
            _userId.value = triple.first
            loadRecipes()
        }
    }

    fun loadRecipes() {
        val staticRecipes = StaticRecipeStore.staticRecipes

        val result = staticRecipes.map { recipeDetail ->

            val ingredientCount = recipeDetail.ingredients.size

            RecipeWithIngredientCount(
                recipe = recipeDetail.recipe,
                ingredientCount = ingredientCount
            )
        }

        _recipes.value = result
    }
}