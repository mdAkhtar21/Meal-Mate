package com.example.mealmate.domain.repository

import com.example.mealmate.domain.model.MealPlan

interface MealPlanRepository {
    suspend fun insertMealPlans(mealPlans: List<MealPlan>): List<Long> // returns rowIds (or -1 for ignored)
    suspend fun getMealPlans(): List<MealPlan>
    suspend fun getMealForDay(day: String): List<MealPlan>
    suspend fun deleteMealPlan(id: Long)
    suspend fun deleteMealPlansForDay(day: String)
    suspend fun clearAllMealPlans(): Unit
    suspend fun isRecipeAlreadyAdded(day: String, recipeId: Long): MealPlan?

    suspend fun updatemealPlan(mealPlan: MealPlan)
}
