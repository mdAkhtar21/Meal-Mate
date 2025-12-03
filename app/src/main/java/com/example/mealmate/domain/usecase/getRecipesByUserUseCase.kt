package com.example.mealmate.domain.usecase

import com.example.mealmate.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipesByUserUseCase @Inject constructor(
    private val repository: RecipeRepository
){
    suspend operator fun invoke(userId:Long)=
        repository.getRecipesByUser(userId)
}