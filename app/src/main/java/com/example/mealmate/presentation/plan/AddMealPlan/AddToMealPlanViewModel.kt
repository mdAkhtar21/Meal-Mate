package com.example.mealmate.presentation.plan.AddMealPlan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.data.datastore.UserPreferences
import com.example.mealmate.domain.model.MealPlan
import com.example.mealmate.domain.usecase.mealPlanUsecase.InsertMealPlansUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddToMealPlanViewModel @Inject constructor(
    private val addRecipeUsecase: InsertMealPlansUseCase,
    private val userPreferences: UserPreferences
):ViewModel() {
    private val _recipePlan= MutableStateFlow<List<MealPlan>>(emptyList())
    private val recipePlan:MutableStateFlow<List<MealPlan>> = _recipePlan

    fun addMealPlans(recipeId: Long, mealType: String, selectedDays: List<String>) {
        viewModelScope.launch {
            val userData = userPreferences.getUser().first()
            val userId = userData.first
            val plans = selectedDays.map { day ->
                MealPlan(
                    recipeId = recipeId,
                    userId = userId,
                    day = day,
                    mealType = mealType
                )
            }
            addRecipeUsecase(plans)
        }
    }



}