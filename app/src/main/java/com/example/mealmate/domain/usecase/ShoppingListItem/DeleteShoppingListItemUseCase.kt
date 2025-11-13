package com.example.mealmate.domain.usecase.ShoppingListItem

import com.example.mealmate.domain.repository.ShoppingListRepository
import javax.inject.Inject

class DeleteShoppingListItemUseCase @Inject constructor(
    private val repository: ShoppingListRepository
) {
    suspend operator fun invoke(id:Long){
        repository.delete(id)
    }
}