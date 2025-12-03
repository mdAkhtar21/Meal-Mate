package com.example.mealmate.data.local.MealPlan

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MealPlanDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMealPlan(mealPlan: MealPlanEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMealPlans(mealPlans: List<MealPlanEntity>): List<Long>

    @Update
    suspend fun updateMealPlans(mealPlans:MealPlanEntity)

    @Query("SELECT * FROM meal_plan ORDER BY day ASC")
    fun getMealPlansFlow(): Flow<List<MealPlanEntity>>


    @Query("SELECT * FROM meal_plan WHERE day = :day AND recipeId = :recipeId LIMIT 1")
    suspend fun isRecipeAlreadyAdded(day: String, recipeId: Long): MealPlanEntity?

    @Query("SELECT * FROM meal_plan ORDER BY id DESC")
    suspend fun getMealPlans(): List<MealPlanEntity>

    @Query("SELECT * FROM meal_plan WHERE day = :day")
    suspend fun getMealPlanForDay(day: String): List<MealPlanEntity>

    @Query("DELETE FROM meal_plan WHERE id = :id")
    suspend fun deleteMealPlan(id: Long)

    @Query("DELETE FROM meal_plan WHERE day = :day")
    suspend fun deleteMealPlansForDay(day: String)

    @Query("DELETE FROM meal_plan")
    suspend fun clearAllMealPlans()
}
