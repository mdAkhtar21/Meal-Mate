package com.example.mealmate.domain.model


data class Instruction(
    val instructionId: Long = 0L,
    val tempKey: Long? = null,
    val recipeId: Long? = null,
    val step: Int,
    val value: String
)