package com.example.mealmate.presentation.home.addrecipie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.data.Mapper.toDomain
import com.example.mealmate.data.datastore.UserPreferences
import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity
import com.example.mealmate.domain.model.FullRecipeDetailDomain
import com.example.mealmate.domain.model.RecipeTable
import com.example.mealmate.domain.usecase.GetRecipesByUserUseCase
import com.example.mealmate.domain.usecase.RecipeUsecase.DeleteRecipeUseCase
import com.example.mealmate.domain.usecase.RecipeUsecase.GetAllRecipesUseCase
import com.example.mealmate.domain.usecase.RecipeUsecase.GetRecipeDetailUseCase
import com.example.mealmate.domain.usecase.RecipeUsecase.InsertRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val insertRecipeUseCase: InsertRecipeUseCase,
    private val getAllRecipesUseCase:GetAllRecipesUseCase,
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val getRecipesByUserUseCase: GetRecipesByUserUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _userId = MutableStateFlow(0L)
    val userId: StateFlow<Long> = _userId

    private val _recipes = MutableStateFlow<List<RecipeTable>>(emptyList())
    val recipes: StateFlow<List<RecipeTable>> = _recipes

    private val _selectedRecipe = MutableStateFlow<FullRecipeDetailDomain?>(null)
    val selectedRecipe: StateFlow<FullRecipeDetailDomain?> = _selectedRecipe

    private val _selectedMealTypes = MutableStateFlow<List<String>>(emptyList())
    val selectedMealTypes: StateFlow<List<String>> = _selectedMealTypes

    init {
        viewModelScope.launch {
            val triple = userPreferences.getUser().first()
            _userId.value = triple.first
            loadRecipes()
        }
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
                recipe = recipeWithMealTypes.toDomain(),
                tempKey = tempKey
            )
            loadRecipes()
        }
    }
    fun loadRecipes() {
        viewModelScope.launch {
            val triple = userPreferences.getUser().first()
            val userId = triple.first
            if (userId != 0L) {
                val recipesList = getRecipesByUserUseCase(userId)
                _recipes.value = recipesList
            }
        }
    }


    fun loadRecipeDetail(recipeId: Long) {
        viewModelScope.launch {
            _selectedRecipe.value = getRecipeDetailUseCase(recipeId)
        }
    }

    fun deleteRecipe(recipe: RecipeTable) {
        viewModelScope.launch {
            deleteRecipeUseCase(recipe)
            loadRecipes()
        }
    }

}


