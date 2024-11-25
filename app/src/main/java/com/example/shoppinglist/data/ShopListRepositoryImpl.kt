package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val _shopList = mutableListOf<ShopItem>()
    val shopList: List<ShopItem> get() = _shopList.toList()

    private var autoincrementId = 0

    init {
        for (i in 0 until 10){
            val item = ShopItem("Name $i", i, true)
            _shopList.add(item)
        }
    }

    override fun getShopItemList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    override fun addShopItem(item: ShopItem) {
        if (item.id == ShopItem.UNDEFINED_ID) {
            item.id = autoincrementId++
        }
        _shopList.add(item)
        updateList()
    }

    override fun deleteShopItem(item: ShopItem) {
        _shopList.remove(item)
        updateList()
    }

    override fun editShopItem(item: ShopItem) {
        val oldElement = getShopItemById(item.id)
        _shopList.remove(oldElement)
        addShopItem(item)
    }

    override fun getShopItemById(id: Int): ShopItem {
        return _shopList.find { it.id == id }
            ?: throw RuntimeException("Element with $id is not found")
    }

    private fun updateList () {
        shopListLD.value = shopList
    }
}