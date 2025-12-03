package com.example.mealmate.data.repository

import com.example.mealmate.data.local.shoppinglist.ShoppingListDao
import com.example.mealmate.data.local.shoppinglist.ShoppingListTable
import com.example.mealmate.domain.model.ShoppingListItem
import com.example.mealmate.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShoppingListRepositoryImpl @Inject constructor(
    private val shoppingListDao: ShoppingListDao
) :ShoppingListRepository{
    override suspend fun insert(shoppingListItem: ShoppingListItem) {
        val entity = ShoppingListTable(
            id = shoppingListItem.id,
            recipeId = shoppingListItem.recipeId,
            ingredientName = shoppingListItem.ingredientName,
            categoryName = shoppingListItem.categoryName,
            quantity = shoppingListItem.quantity,
            comment = shoppingListItem.comment?:"",
            isChecked = shoppingListItem.isChecked,
            userId = shoppingListItem.userId
        )
        shoppingListDao.insertIngredient(entity)
    }

    override suspend fun update(shoppingListItem: ShoppingListItem) {
        val entity=ShoppingListTable(
            id = shoppingListItem.id,
            recipeId = shoppingListItem.recipeId,
            ingredientName = shoppingListItem.ingredientName,
            categoryName = shoppingListItem.categoryName,
            quantity = shoppingListItem.quantity,
            comment = shoppingListItem.comment?:"",
            isChecked = shoppingListItem.isChecked,
            userId = shoppingListItem.userId
        )
        shoppingListDao.updateShoppingList(entity)
    }

    override fun getAllShoppingListItems(): Flow<List<ShoppingListItem>> {
      return shoppingListDao.getAllShoppingListItems()
            .map { list->
                list.map { entity->
                    ShoppingListItem(
                        id = entity.id,
                        recipeId = entity.recipeId,
                        ingredientName = entity.ingredientName,
                        categoryName = entity.categoryName,
                        quantity = entity.quantity,
                        comment = entity.comment,
                        isChecked = entity.isChecked,
                        userId = entity.userId
                    )
                }
            }
    }

    override suspend fun delete(id: Long) {
        shoppingListDao.deleteShoppingListItem(id)
    }

    override suspend fun deleteAll() {
        shoppingListDao.deleteAllShoppingListItems()
    }

}