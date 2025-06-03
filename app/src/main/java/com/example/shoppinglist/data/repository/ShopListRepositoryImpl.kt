package com.example.shoppinglist.data.repository

import com.example.shoppinglist.data.database.ShopListDao
import com.example.shoppinglist.data.mapper.ShopListMapper
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper,
) : ShopListRepository {


    override fun getShopItemList(): Flow<List<ShopItem>> {
        return shopListDao.getShopItemList().map { dbList ->
            mapper.mapListDbModelToListEntity(dbList)
        }
    }

    override suspend fun addShopItem(item: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(item))
    }

    override suspend fun deleteShopItem(item: ShopItem) {
        shopListDao.deleteShopItem(item.id)
    }

    override suspend fun editShopItem(item: ShopItem) {
        addShopItem(item)
    }

    override suspend fun getShopItemById(id: Int): ShopItem {
        val shopItemDbModel = shopListDao.getShopItemById(id)
        return mapper.mapDbModelToEntity(shopItemDbModel)
    }

}