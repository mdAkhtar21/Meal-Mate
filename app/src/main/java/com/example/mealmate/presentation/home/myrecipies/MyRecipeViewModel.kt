package com.example.mealmate.presentation.home.myrecipies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.data.Mapper.toDomain
import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity
import com.example.mealmate.domain.model.RecipeTable
import com.example.mealmate.domain.model.RecipeWithIngredientCount
import com.example.mealmate.domain.usecase.IngredientUseCase.GetIngredientsForRecipeUseCase
import com.example.mealmate.domain.usecase.LoginUserUseCase
import com.example.mealmate.domain.usecase.RecipeUsecase.DeleteRecipeUseCase
import com.example.mealmate.domain.usecase.RecipeUsecase.GetAllRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyRecipeViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val getAllRecipesUseCase: GetAllRecipesUseCase,
    private val getIngredientsForRecipeUseCase: GetIngredientsForRecipeUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<RecipeWithIngredientCount>>(emptyList())
    val recipes: StateFlow<List<RecipeWithIngredientCount>> = _recipes



    fun loadRecipes() {
        viewModelScope.launch {
            val userid=loginUserUseCase.getLoggedInUser().first().first
            val recipeList = getAllRecipesUseCase(userid)

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
    fun deleteRecipe(recipe: RecipeTable) {
        viewModelScope.launch {
            deleteRecipeUseCase(recipe)
            loadRecipes()
        }
    }
}
