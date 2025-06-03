package com.example.shoppinglist.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShopItemListUseCase @Inject constructor
    (private val shopListRepository: ShopListRepository) {

    fun getShopItemList(): Flow<List<ShopItem>> {
       return shopListRepository.getShopItemList()
    }
}