package com.example.shoppinglist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.shoppinglist.Di.ApplicationScope
import com.example.shoppinglist.data.database.ShopListDao
import com.example.shoppinglist.data.mapper.ShopListMapper
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import javax.inject.Inject


@ApplicationScope
class ShopListRepositoryImpl @Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper
) : ShopListRepository {


    override fun getShopItemList(): LiveData<List<ShopItem>> {
        return MediatorLiveData<List<ShopItem>>().apply {
            addSource(shopListDao.getShopItemList()) {
                value = mapper.mapListDbModelToListEntity(it)
            }
        }
    }

//    override fun getShopItemList(): LiveData<List<ShopItem>> {
//        return shopListDao.getShopItemList().map {
//            mapper.mapListDbModelToListEntity(it)
//        }
//    }


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