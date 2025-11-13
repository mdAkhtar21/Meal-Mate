package com.example.mealmate.data.local.shoppinglist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(shoppingListTable: ShoppingListTable)

    @Update
    suspend fun updateShoppingList(shoppingListTable: ShoppingListTable)

    @Query("DELETE FROM shopping_list WHERE id = :id")
    suspend fun deleteShoppingListItem(id: Long)

    @Query("SELECT * FROM shopping_list")
    fun getAllShoppingListItems(): Flow<List<ShoppingListTable>>
}
