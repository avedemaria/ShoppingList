package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

    private val _shopList = mutableListOf<ShopItem>()
    val shopList: List<ShopItem> get() = _shopList

    private var autoincrementId = 0


    override fun addShopItem(item: ShopItem) {
        if (item.id == ShopItem.UNDEFINED_ID)
        item.id = autoincrementId++
        _shopList.add(item)
    }

    override fun deleteShopItem(item: ShopItem) {
        _shopList.remove(item)
    }

    override fun editShopItem(item: ShopItem) {
        val oldElement = getShopItemById(item.id)
        _shopList.remove(oldElement)
        addShopItem(item)
    }

    override fun getShopItemList(): List<ShopItem> {
        return _shopList
    }

    override fun getShopItemById(id: Int): ShopItem {
        return _shopList.find { it.id == id }
            ?: throw RuntimeException("Element with $id is not found")
    }
}