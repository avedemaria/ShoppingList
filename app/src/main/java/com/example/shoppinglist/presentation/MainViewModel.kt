package com.example.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.repository.ShopListRepositoryImpl
import com.example.shoppinglist.domain.DeleteShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetIShopItemListUseCase
import com.example.shoppinglist.domain.GetShopItemByIdUseCase
import com.example.shoppinglist.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShopListUseCase: GetIShopItemListUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase

) : ViewModel() {


    val shopListLD = getShopListUseCase.getShopItemList()


    fun deleteShopItem(item: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(item)
        }

    }

    fun changeEnableState(item: ShopItem) {
        viewModelScope.launch {
            val newItem = item.copy(isEnabled = !item.isEnabled)
            editShopItemUseCase.editShopItem(newItem)
        }

    }


}