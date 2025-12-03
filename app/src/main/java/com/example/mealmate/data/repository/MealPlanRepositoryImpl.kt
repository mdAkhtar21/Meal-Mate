package com.example.mealmate.data.repository

import com.example.mealmate.data.local.MealPlan.MealPlanDao
import com.example.mealmate.data.local.MealPlan.MealPlanEntity
import com.example.mealmate.domain.model.MealPlan
import com.example.mealmate.domain.repository.MealPlanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MealPlanRepositoryImpl @Inject constructor(
    private val dao: MealPlanDao
) : MealPlanRepository {

    override suspend fun insertMealPlans(mealPlans: List<MealPlan>): List<Long> {
        val entities = mealPlans.map { domain ->
            MealPlanEntity(
                id = domain.id,
                recipeId = domain.recipeId,
                mealType = domain.mealType,
                day = domain.day,
                userId = domain.userId
            )
        }
        return dao.insertMealPlans(entities)
    }

    override suspend fun getMealPlans(): List<MealPlan> {
        return dao.getMealPlans().map {
            MealPlan(
                id = it.id,
                recipeId = it.recipeId,
                mealType = it.mealType,
                day = it.day,
                userId = it.userId
            )
        }
    }

    override suspend fun getMealForDay(day: String): List<MealPlan> {
        return dao.getMealPlanForDay(day).map {
            MealPlan(
                id = it.id,
                recipeId = it.recipeId,
                mealType = it.mealType,
                day = it.day,
                userId = it.userId
            )
        }
    }

    override suspend fun deleteMealPlan(id: Long) {
        dao.deleteMealPlan(id)
    }

    override suspend fun deleteMealPlansForDay(day: String) {
        dao.deleteMealPlansForDay(day)
    }

    override suspend fun clearAllMealPlans() {
        dao.clearAllMealPlans()
    }

    override suspend fun isRecipeAlreadyAdded(day: String, recipeId: Long): MealPlan? {
        return dao.isRecipeAlreadyAdded(day, recipeId)?.let {
            MealPlan(
                id = it.id,
                recipeId = it.recipeId,
                mealType = it.mealType,
                day = it.day,
                userId = it.userId
            )
        }
    }

    override fun getMealPlansFlow(): Flow<List<MealPlan>> {
        return dao.getMealPlansFlow().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun updatemealPlan(mealPlan: MealPlan) {
       val entity = MealPlanEntity(
           id = mealPlan.id,
           recipeId = mealPlan.recipeId,
           mealType = mealPlan.mealType,
           day = mealPlan.day,
           userId = mealPlan.userId
       )
        dao.updateMealPlans(entity)
    }
}

fun MealPlanEntity.toDomain(): MealPlan {
    return MealPlan(
        id = this.id,
        recipeId = this.recipeId,
        mealType = this.mealType,
        day = this.day,
        userId = this.userId
    )
}