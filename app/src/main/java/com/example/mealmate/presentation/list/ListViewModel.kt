package com.example.mealmate.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.data.datastore.UserPreferences
import com.example.mealmate.domain.model.RecipeTable
import com.example.mealmate.domain.model.ShoppingListItem
import com.example.mealmate.domain.usecase.GetRecipesByUserUseCase
import com.example.mealmate.domain.usecase.ShoppingListItem.DeleteAllShoppingListItem
import com.example.mealmate.domain.usecase.ShoppingListItem.DeleteShoppingListItemUseCase
import com.example.mealmate.domain.usecase.ShoppingListItem.GetAllShoppingListItemUseCase
import com.example.mealmate.domain.usecase.ShoppingListItem.UpdateShoppingListItemUseCase
import com.example.mealmate.domain.usecase.ShoppingListItem.InsertShoppingListItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val insertUsecase:InsertShoppingListItemUseCase,
    private val updateUsecase:UpdateShoppingListItemUseCase,
    private val deleteUsecase:DeleteShoppingListItemUseCase,
    private val getAllUsecase: GetAllShoppingListItemUseCase,
    private val deleteAllShoppingListItem:DeleteAllShoppingListItem,
    private val getRecipesByUserUseCase: GetRecipesByUserUseCase,
    private val userPreferences: UserPreferences
): ViewModel()
{
    private val _shoppingListItems = MutableStateFlow<List<ShoppingListItem>>(emptyList())
    val shoppingListItems: StateFlow<List<ShoppingListItem>> = _shoppingListItems.asStateFlow()

    private val _userId = MutableStateFlow(0L)
    val userId: StateFlow<Long> = _userId

    init {
        getAllShoppingListItems()
    }

    fun insertShoppingListItem(item: ShoppingListItem){
        viewModelScope.launch {
            val triple = userPreferences.getUser().first()
            val userId = triple.first

            val newItem = item.copy(
                userId = userId
            )
            insertUsecase(newItem)
            getAllShoppingListItems()
        }
    }
    fun updateShoppingListItem(item:ShoppingListItem){
        viewModelScope.launch {
            updateUsecase(item)
            getAllShoppingListItems()
        }
    }
    fun deleteShoppingListItem(id:Long){
        viewModelScope.launch {
            deleteUsecase(id)
            getAllShoppingListItems()
        }
    }

   private fun getAllShoppingListItems(){
        viewModelScope.launch {
            val triple = userPreferences.getUser().first()
            val userId = triple.first
            _userId.value = userId
            getAllUsecase().collect { list ->
                _shoppingListItems.value = list.filter { item ->
                    item.userId == userId
                }
            }
        }
   }
    fun deleteAllShoppingList(){
        viewModelScope.launch {
            deleteAllShoppingListItem()
            getAllShoppingListItems()
        }
    }
}