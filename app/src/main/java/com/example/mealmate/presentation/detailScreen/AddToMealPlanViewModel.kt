package com.example.mealmate.presentation.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.MealPlan
import com.example.mealmate.domain.usecase.mealPlanUsecase.InsertMealPlansUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddToMealPlanViewModel @Inject constructor(
    private val addRecipeUsecase: InsertMealPlansUseCase
):ViewModel() {
    private val _recipePlan= MutableStateFlow<List<MealPlan>>(emptyList())
    private val recipePlan:MutableStateFlow<List<MealPlan>> = _recipePlan

    fun addMealPlans(recipeId: Long, mealType: String, selectedDays: List<String>) {
        viewModelScope.launch {
            val plans = selectedDays.map { day ->
                MealPlan(
                    recipeId = recipeId,
                    day = day,
                    mealType = mealType
                )
            }
            addRecipeUsecase(plans)
        }
    }



}