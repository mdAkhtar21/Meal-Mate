package com.example.mealmate.domain.usecase.mealPlanUsecase

import com.example.mealmate.domain.repository.MealPlanRepository
import javax.inject.Inject

class GetMealPlanForDayUseCase @Inject constructor(
    private val repository: MealPlanRepository
) {
    suspend operator fun invoke(day: String) = repository.getMealForDay(day)
}