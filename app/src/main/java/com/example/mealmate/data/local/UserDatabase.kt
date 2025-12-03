package com.example.mealmate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mealmate.data.Mapper.Converters
import com.example.mealmate.data.local.Ingredient.IngredientDao
import com.example.mealmate.data.local.Ingredient.IngredientTableEntity
import com.example.mealmate.data.local.Instruction.InstructionDao
import com.example.mealmate.data.local.Instruction.InstructionTableEntity
import com.example.mealmate.data.local.AddRecipe.RecipeDao
import com.example.mealmate.data.local.RecipeSource.RecipeSourceEntity
import com.example.mealmate.data.local.RecipeSource.RecipeSourceDao
import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity
import com.example.mealmate.data.local.Auth.UserDao
import com.example.mealmate.data.local.Auth.UserEntity
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
    RecipeSourceEntity::class], version = 5, exportSchema = false)
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