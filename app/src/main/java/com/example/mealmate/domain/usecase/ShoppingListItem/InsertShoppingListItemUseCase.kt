package com.example.mealmate.domain.usecase.ShoppingListItem

import com.example.mealmate.domain.model.ShoppingListItem
import com.example.mealmate.domain.repository.ShoppingListRepository
import javax.inject.Inject

class InsertShoppingListItemUseCase @Inject constructor(
    private val repository: ShoppingListRepository
) {
    suspend operator fun invoke(shoppingListItem: ShoppingListItem) {
        repository.insert(shoppingListItem)
    }
}
