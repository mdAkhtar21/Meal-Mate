package com.example.mealmate.data.Mapper

import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity
import com.example.mealmate.data.local.Ingredient.IngredientTableEntity
import com.example.mealmate.data.local.Instruction.InstructionTableEntity
import com.example.mealmate.data.local.RecipeSource.RecipeSourceEntity
import com.example.mealmate.domain.model.FullRecipeDetailDomain
import com.example.mealmate.domain.model.Ingredient
import com.example.mealmate.domain.model.Instruction
import com.example.mealmate.domain.model.RecipeDetail
import com.example.mealmate.domain.model.RecipeSource
import com.example.mealmate.domain.model.RecipeTable

fun RecipeTable.toEntity(): RecipeTableEntity {
    return RecipeTableEntity(
        recipeId = this.recipeId,
        userId = this.userId,
        title = this.title,
        description = this.description,
        recipeImage = this.recipeImage,
        category = this.category,
        style = this.style,
        mealType = this.mealType,
        serving = this.serving,
        preprationTime = this.preprationTime,
        cookingTime = this.cookingTime
    )
}

/** ---------------- Ingredient ---------------- */
fun Ingredient.toEntity(): IngredientTableEntity {
    return IngredientTableEntity(
        ingredientId = this.ingredientId,
        tempKey = null,
        recipeId = this.recipeId,
        name = this.name,
        category = this.category,
        measurement = this.measurement
    )
}

/** ---------------- Instruction ---------------- */
fun Instruction.toEntity(): InstructionTableEntity {
    return InstructionTableEntity(
        instructionId = this.instructionId,
        tempKey = null,
        recipeId = this.recipeId,
        step = this.step,
        description = this.value
    )
}

/** ---------------- RecipeSourceTable ---------------- */
fun RecipeSource.toEntity(): RecipeSourceEntity {
    return RecipeSourceEntity(
        sourceId = this.sourceId,
        tempKey = this.tempKey,
        recipeId = this.recipeId,
        name = this.name,
        url = this.url
    )
}

/** ---------------- FullRecipeDetail Converter ---------------- */
fun RecipeDetail.toFullRecipeDetail(): FullRecipeDetailDomain {
    return FullRecipeDetailDomain(
        recipe = this.recipe,
        ingredients = this.ingredients,
        instructions = this.instructions,
        sources = this.sources
    )
}