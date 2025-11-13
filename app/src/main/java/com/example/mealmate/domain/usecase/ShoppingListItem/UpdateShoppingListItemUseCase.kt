package com.example.mealmate.domain.usecase.ShoppingListItem

import com.example.mealmate.domain.model.ShoppingListItem
import com.example.mealmate.domain.repository.ShoppingListRepository
import javax.inject.Inject

class UpdateShoppingListItemUseCase @Inject constructor(
    private val repository: ShoppingListRepository
) {
    suspend operator fun invoke(shoppingListItem: ShoppingListItem) {
        repository.update(shoppingListItem)
    }
}