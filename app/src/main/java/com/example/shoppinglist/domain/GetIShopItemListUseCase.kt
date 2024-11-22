package com.example.shoppinglist.domain

class GetIShopItemListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopItemList(): List<ShopItem> {
       return shopListRepository.getShopItemList()
    }
}