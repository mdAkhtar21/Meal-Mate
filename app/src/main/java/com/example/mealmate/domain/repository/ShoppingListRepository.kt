package com.example.mealmate.domain.repository

import com.example.mealmate.domain.model.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {

    suspend fun insert(shoppingListItem: ShoppingListItem)

    suspend fun update(shoppingListItem: ShoppingListItem)

    fun getAllShoppingListItems(): Flow<List<ShoppingListItem>>

    suspend fun delete(id: Long)

    suspend fun deleteAll()
}
