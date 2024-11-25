package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ShopListRepository {

    fun addShopItem (item:ShopItem)
    fun deleteShopItem (item:ShopItem)
    fun editShopItem (item:ShopItem)
    fun getShopItemList(): LiveData<List<ShopItem>>
    fun getShopItemById (id:Int): ShopItem

}