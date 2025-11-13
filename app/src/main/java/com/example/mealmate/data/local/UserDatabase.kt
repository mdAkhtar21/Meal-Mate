package com.example.mealmate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mealmate.data.local.AddRecipe.Converters
import com.example.mealmate.data.local.AddRecipe.IngredientDao
import com.example.mealmate.data.local.AddRecipe.IngredientTableEntity
import com.example.mealmate.data.local.AddRecipe.InstructionDao
import com.example.mealmate.data.local.AddRecipe.InstructionTableEntity
import com.example.mealmate.data.local.AddRecipe.RecipeDao
import com.example.mealmate.data.local.AddRecipe.RecipeSource
import com.example.mealmate.data.local.AddRecipe.RecipeSourceDao
import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity
import com.example.mealmate.data.local.MealPlan.MealPlanDao
import com.example.mealmate.data.local.MealPlan.MealPlanEntity
import com.example.mealmate.data.local.shoppinglist.ShoppingListDao
import com.example.mealmate.data.local.shoppinglist.ShoppingListTable

@Database(entities = [UserEntity::class,
    RecipeTableEntity::class,
    IngredientTableEntity::class,
    InstructionTableEntity::class,
    MealPlanEntity::class,
    ShoppingListTable::class,
    RecipeSource::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun instructionDao(): InstructionDao
    abstract fun recipeSourceDao(): RecipeSourceDao
    abstract fun mealPlanDao():MealPlanDao
    abstract fun shoppingListDao():ShoppingListDao
}