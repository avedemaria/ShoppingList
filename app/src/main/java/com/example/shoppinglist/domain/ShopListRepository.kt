package com.example.shoppinglist.domain

interface ShopListRepository {

    fun addShopItem (item:ShopItem)
    fun deleteShopItem (item:ShopItem)
    fun editShopItem (item:ShopItem)
    fun getShopItemList(): List<ShopItem>
    fun getShopItemById (id:Int): ShopItem

}