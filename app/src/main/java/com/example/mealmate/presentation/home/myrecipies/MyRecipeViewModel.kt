package com.example.mealmate.presentation.home.myrecipies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity
import com.example.mealmate.domain.usecase.IngredientUseCase.GetIngredientsForRecipeUseCase
import com.example.mealmate.domain.usecase.RecipeUsecase.GetAllRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Model: Recipe + Ingredient Count
data class RecipeWithIngredientCount(
    val recipe: RecipeTableEntity,
    val ingredientCount: Int
)

@HiltViewModel
class MyRecipeViewModel @Inject constructor(
    private val getAllRecipesUseCase: GetAllRecipesUseCase,
    private val getIngredientsForRecipeUseCase: GetIngredientsForRecipeUseCase
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<RecipeWithIngredientCount>>(emptyList())
    val recipes: StateFlow<List<RecipeWithIngredientCount>> = _recipes

    fun loadRecipes() {
        viewModelScope.launch {
            val recipeList = getAllRecipesUseCase()

            val result = recipeList.map { recipe ->
                val ingredients = getIngredientsForRecipeUseCase.getByRecipeId(recipe.recipeId)

                RecipeWithIngredientCount(
                    recipe = recipe,
                    ingredientCount = ingredients.count { it.recipeId == recipe.recipeId }
                )
            }

            _recipes.value = result
        }
    }
}
