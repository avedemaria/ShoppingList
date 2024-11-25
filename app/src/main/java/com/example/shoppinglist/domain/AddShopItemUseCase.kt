package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun addShopItem(item: ShopItem) {
        shopListRepository.addShopItem(item)
    }
}