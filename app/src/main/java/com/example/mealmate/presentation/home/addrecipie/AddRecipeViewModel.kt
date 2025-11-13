package com.example.mealmate.presentation.home.addrecipie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity
import com.example.mealmate.domain.usecase.RecipeUsecase.DeleteRecipeUseCase
import com.example.mealmate.domain.usecase.RecipeUsecase.FullRecipeDetail
import com.example.mealmate.domain.usecase.RecipeUsecase.GetAllRecipesUseCase
import com.example.mealmate.domain.usecase.RecipeUsecase.GetRecipeDetailUseCase
import com.example.mealmate.domain.usecase.RecipeUsecase.InsertRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val insertRecipeUseCase: InsertRecipeUseCase,
    private val getAllRecipesUseCase:GetAllRecipesUseCase,
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<RecipeTableEntity>>(emptyList())
    val recipes: StateFlow<List<RecipeTableEntity>> = _recipes

    private val _selectedRecipe = MutableStateFlow<FullRecipeDetail?>(null)
    val selectedRecipe: StateFlow<FullRecipeDetail?> = _selectedRecipe

    private val _selectedMealTypes = MutableStateFlow<List<String>>(emptyList())
    val selectedMealTypes: StateFlow<List<String>> = _selectedMealTypes

    init {
        loadRecipes()
    }

    fun toggleMealType(mealType: String) {
        val current = _selectedMealTypes.value.toMutableList()
        if (current.contains(mealType)) {
            current.remove(mealType)
        } else {
            current.add(mealType)
        }
        _selectedMealTypes.value = current
    }


    fun insertRecipe(recipe: RecipeTableEntity, tempKey: Long) {
        viewModelScope.launch {
            val recipeWithMealTypes = recipe.copy(mealType = _selectedMealTypes.value)

            val recipeId = insertRecipeUseCase(
                recipe = recipeWithMealTypes,
                tempKey = tempKey
            )


            loadRecipes()
        }
    }




    fun loadRecipes() {
        viewModelScope.launch {
            _recipes.value = getAllRecipesUseCase()
        }
    }

    fun loadRecipeDetail(recipeId: Long) {
        viewModelScope.launch {
            _selectedRecipe.value = getRecipeDetailUseCase(recipeId)
        }
    }

    fun deleteRecipe(recipe: RecipeTableEntity) {
        viewModelScope.launch {
            deleteRecipeUseCase(recipe)
            loadRecipes()
        }
    }

}
