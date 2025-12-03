package com.example.mealmate.domain.usecase.mealPlanUsecase

import com.example.mealmate.domain.model.MealPlan
import com.example.mealmate.domain.repository.MealPlanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMealPlansGroupedByDayUseCase @Inject constructor(
    private val repository: MealPlanRepository
) {
    operator fun invoke(): Flow<Map<String, List<MealPlan>>> {
        return repository
            .getMealPlansFlow()
            .map { list -> list.groupBy { it.day } }
    }
}
