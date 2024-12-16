package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ShopListRepository {

    suspend fun addShopItem(item: ShopItem)
    suspend fun deleteShopItem(item: ShopItem)
    suspend fun editShopItem(item: ShopItem)
    fun getShopItemList(): LiveData<List<ShopItem>>
    suspend fun getShopItemById(id: Int): ShopItem

}