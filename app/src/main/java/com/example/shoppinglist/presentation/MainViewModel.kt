package com.example.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.DeleteShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetIShopItemListUseCase
import com.example.shoppinglist.domain.ShopItem

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val shopListRepository = ShopListRepositoryImpl

    private val getShopListUseCase = GetIShopItemListUseCase(shopListRepository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(shopListRepository)
    private val addShopItemUseCase = AddShopItemUseCase(shopListRepository)
    private val editShopItemUseCase = EditShopItemUseCase(shopListRepository)

    val shopListLD = getShopListUseCase.getShopItemList()



    fun deleteShopItem (item:ShopItem) {
        deleteShopItemUseCase.deleteShopItem(item)
    }

    fun changeEnableState (item:ShopItem) {
        val newItem = item.copy(isEnabled = !item.isEnabled)
        editShopItemUseCase.editShopItem(newItem)

    }

}