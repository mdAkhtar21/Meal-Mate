package com.example.mealmate.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.domain.model.ShoppingListItem
import com.example.mealmate.domain.usecase.ShoppingListItem.DeleteShoppingListItemUseCase
import com.example.mealmate.domain.usecase.ShoppingListItem.GetAllShoppingListItemUseCase
import com.example.mealmate.domain.usecase.ShoppingListItem.UpdateShoppingListItemUseCase
import com.example.mealmate.domain.usecase.ShoppingListItem.InsertShoppingListItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val insertUsecase:InsertShoppingListItemUseCase,
    private val updateUsecase:UpdateShoppingListItemUseCase,
    private val deleteUsecase:DeleteShoppingListItemUseCase,
    private val getAllUsecase: GetAllShoppingListItemUseCase
): ViewModel()
{

    private val _shoppingListItems = MutableStateFlow<List<ShoppingListItem>>(emptyList())
    val shoppingListItems: StateFlow<List<ShoppingListItem>> = _shoppingListItems.asStateFlow()

    init {
        getAllShoppingListItems()
    }

    fun insertShoppingListItem(item: ShoppingListItem){
        viewModelScope.launch {
            insertUsecase(item)
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
            getAllUsecase().collect{
                _shoppingListItems.value=it
            }
        }
    }
}