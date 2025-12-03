package com.example.mealmate.domain.usecase.ShoppingListItem

import com.example.mealmate.domain.repository.ShoppingListRepository
import javax.inject.Inject

class DeleteAllShoppingListItem @Inject constructor(
    private val repository: ShoppingListRepository
) {
    suspend operator fun invoke(){
        repository.deleteAll()
    }
}