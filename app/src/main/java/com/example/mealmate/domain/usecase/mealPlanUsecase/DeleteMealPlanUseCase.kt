package com.example.mealmate.domain.usecase.mealPlanUsecase

import com.example.mealmate.domain.repository.MealPlanRepository
import javax.inject.Inject

class DeleteMealPlanUseCase @Inject constructor(
    private val repository: MealPlanRepository
) {
    suspend operator fun invoke(id: Long) = repository.deleteMealPlan(id)
}
