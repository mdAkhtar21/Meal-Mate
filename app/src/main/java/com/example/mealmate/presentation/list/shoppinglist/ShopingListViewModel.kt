package com.example.mealmate.presentation.list.shoppinglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.data.datastore.UserPreferences
import com.example.mealmate.domain.model.Ingredient
import com.example.mealmate.domain.usecase.IngredientUseCase.GetIngredientsForRecipeUseCase
import com.example.mealmate.domain.usecase.RecipeUsecase.GetAllRecipesUseCase
import com.example.mealmate.presentation.home.foryouscreen.StaticRecipeStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val getIngredientsForRecipeUseCase: GetIngredientsForRecipeUseCase,
    private val getRecipeByIdUseCase: GetAllRecipesUseCase,
    private val loginUserUseCase: UserPreferences
) : ViewModel() {

    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredients: StateFlow<List<Ingredient>> = _ingredients

    // ✅ Load ingredients for multiple recipes at once
    fun loadIngredientsForRecipes(recipeIds: List<Long>) {
        viewModelScope.launch {
            // Get current user id
            val currentUserId = loginUserUseCase.getUser().first().first

            // Get all recipes of current user
            val userRecipes = getRecipeByIdUseCase(currentUserId)

            val allIngredients = recipeIds.distinct().flatMap { recipeId ->
                val recipe = userRecipes.find { it.recipeId == recipeId }
                if (recipe != null && recipe.userId == currentUserId) {
                    getIngredientsForRecipeUseCase.getByRecipeId(recipeId)
                } else emptyList()
            }

            val staticIngredients = recipeIds.distinct().flatMap { recipeId ->
                StaticRecipeStore.staticRecipes
                    .find { it.recipe.recipeId == recipeId && it.recipe.userId == currentUserId }
                    ?.ingredients ?: emptyList()
            }

            _ingredients.value = allIngredients + staticIngredients
        }
    }
}
