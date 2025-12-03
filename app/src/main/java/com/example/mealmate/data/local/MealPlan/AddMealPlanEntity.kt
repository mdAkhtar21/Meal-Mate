package com.example.mealmate.data.local.MealPlan

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "meal_plan",
    indices = [Index(value = ["userId", "recipeId", "day", "meal_type"], unique = true)]
)
data class MealPlanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val recipeId: Long,
    val userId: Long,
    @ColumnInfo(name = "meal_type")
    val mealType: String,
    val day: String
)
