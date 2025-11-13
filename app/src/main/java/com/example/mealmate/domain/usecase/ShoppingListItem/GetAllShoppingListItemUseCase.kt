package com.example.mealmate.domain.usecase.ShoppingListItem

import com.example.mealmate.domain.model.ShoppingListItem
import com.example.mealmate.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllShoppingListItemUseCase @Inject constructor(
    private val repository: ShoppingListRepository
) {
    suspend operator fun invoke():Flow<List<ShoppingListItem>>{
        return repository.getAllShoppingListItems()
    }
}