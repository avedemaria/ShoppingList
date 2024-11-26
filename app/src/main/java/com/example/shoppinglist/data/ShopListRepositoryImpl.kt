package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()

    private val shopList = mutableListOf<ShopItem>()
//    val shopList: List<ShopItem> get() = _shopList.toList()

    private var autoincrementId = 0

    init {
        for (i in 0 until 100){
            val item = ShopItem("Name $i", i, Random.nextBoolean())
            shopList.add(item)
        }
    }

    override fun getShopItemList(): LiveData<List<ShopItem>> {
        updateList()
        return shopListLD
    }

    override fun addShopItem(item: ShopItem) {
        if (item.id == ShopItem.UNDEFINED_ID) {
            item.id = autoincrementId++
        }
        shopList.add(item)
        updateList()
    }

    override fun deleteShopItem(item: ShopItem) {
        shopList.remove(item)
        updateList()
    }

    override fun editShopItem(item: ShopItem) {
        val oldElement = getShopItemById(item.id)
        shopList.remove(oldElement)
        addShopItem(item)
    }

    override fun getShopItemById(id: Int): ShopItem {
        return shopList.find { it.id == id }
            ?: throw RuntimeException("Element with $id is not found")
    }

    private fun updateList () {
        shopListLD.value = shopList.toList()
    }
}