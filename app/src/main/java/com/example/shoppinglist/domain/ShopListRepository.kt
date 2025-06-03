package com.example.shoppinglist.domain

import kotlinx.coroutines.flow.Flow

interface ShopListRepository {

    suspend fun addShopItem(item: ShopItem)
    suspend fun deleteShopItem(item: ShopItem)
    suspend fun editShopItem(item: ShopItem)
    fun getShopItemList(): Flow<List<ShopItem>>
    suspend fun getShopItemById(id: Int): ShopItem

}