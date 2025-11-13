package com.example.mealmate.domain.usecase.mealPlanUsecase

import com.example.mealmate.domain.model.MealPlan
import com.example.mealmate.domain.repository.MealPlanRepository
import javax.inject.Inject

class InsertMealPlansUseCase @Inject constructor(
    private val repository: MealPlanRepository
) {
    suspend operator fun invoke(mealPlans: List<MealPlan>): List<Long> {
        return repository.insertMealPlans(mealPlans)
    }
}
