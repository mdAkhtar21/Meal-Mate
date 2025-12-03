package com.example.mealmate.data.local.LocalData

import android.content.Context
import com.example.mealmate.R
import com.example.mealmate.domain.model.Ingredient
import com.example.mealmate.domain.model.Instruction
import com.example.mealmate.domain.model.RecipeDetail
import com.example.mealmate.domain.model.RecipeSource
import com.example.mealmate.domain.model.RecipeTable
import com.example.mealmate.utils.ImageUtils


object SampleRecipes {

    fun getForYouRecipes(context: Context,userId: Long): List<RecipeDetail> {
        val maggieImage = ImageUtils.drawableToByteArray(R.drawable.maggies, context)

        return listOf(
            RecipeDetail(
                recipe = RecipeTable(
                    recipeId = 101,
                    userId=userId,
                    title = "Maggie",
                    description = "Quick and tasty noodles.",
                    recipeImage = maggieImage,
                    category = "Fast Food",
                    style = "Indian",
                    mealType = listOf("Breakfast", "Snacks"),
                    serving = 1,
                    preprationTime = "2 mins",
                    cookingTime = "3 mins"
                ),
                ingredients = listOf(
                    Ingredient(1, null, 101, "Noodles", "Main", "1 Packet"),
                    Ingredient(2, null, 101, "Water", "Liquid", "200 ml")
                ),
                instructions = listOf(
                    Instruction(1, null, 101, 1, "Boil water."),
                    Instruction(2, null, 101, 2, "Add noodles and cook.")
                ),
                sources = listOf(
                    RecipeSource(1, null, 101, "Nestle Website", "https://maggie.com")
                )
            )
        )
    }
}