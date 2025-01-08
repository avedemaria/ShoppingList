package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetIShopItemListUseCase @Inject constructor
    (private val shopListRepository: ShopListRepository) {

    fun getShopItemList(): LiveData<List<ShopItem>> {
       return shopListRepository.getShopItemList()
    }
}