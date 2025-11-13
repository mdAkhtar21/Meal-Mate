package com.example.mealmate.presentation.home.addrecipie.ingredient

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.Ingredient
import com.example.mealmate.domain.usecase.IngredientUseCase.AddIngredientUseCase
import com.example.mealmate.domain.usecase.IngredientUseCase.DeleteIngredientUseCase
import com.example.mealmate.domain.usecase.IngredientUseCase.GetIngredientsForRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class IngredientViewModel @Inject constructor(
    private val addIngredientUseCase: AddIngredientUseCase,
    private val deleteIngredientUseCase:DeleteIngredientUseCase,
    private val getIngredientsForRecipeUseCase: GetIngredientsForRecipeUseCase
):ViewModel() {

    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredient: StateFlow<List<Ingredient>> = _ingredients


    fun loadIngredients(tempKey: Long){
        viewModelScope.launch {
            _ingredients.value=getIngredientsForRecipeUseCase.getByTempKey(tempKey)
        }
    }

    fun addIngredient(tempKey: Long,name:String,category: String,measurement: String){
        viewModelScope.launch {
            try {
                val ingredient = Ingredient(
                    ingredientId = 0L,
                    recipeId =null ,
                    tempKey = tempKey,
                    name = name,
                    category = category,
                    measurement = measurement
                )
                addIngredientUseCase(ingredient)
                loadIngredients(tempKey)
            } catch (e: Exception) {
                Log.e("IngredientVM", "Failed to add ingredient", e)
            }
        }
    }
    fun deleteIngredient(tempKey:Long, ingredientId: Long) {
        viewModelScope.launch {
            deleteIngredientUseCase(tempKey,ingredientId)
            loadIngredients(tempKey)
        }
    }
}