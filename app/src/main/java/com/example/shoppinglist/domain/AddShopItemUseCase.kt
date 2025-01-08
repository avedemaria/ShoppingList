package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class AddShopItemUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {

    suspend fun addShopItem(item: ShopItem) {
        shopListRepository.addShopItem(item)
    }
}