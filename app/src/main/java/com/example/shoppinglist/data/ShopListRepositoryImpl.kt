package com.example.shoppinglist.data

import android.app.Application
import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import kotlin.random.Random

class ShopListRepositoryImpl(application: Application) : ShopListRepository {


    private val shopListDao = AppDataBase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()


    override fun getShopItemList(): LiveData<List<ShopItem>> {
        return MediatorLiveData<List<ShopItem>>().apply {
            addSource(shopListDao.getShopItemList(), {
                mapper.mapListDbModelToListEntity(it)
            })
        }
    }

//    override fun getShopItemList(): LiveData<List<ShopItem>> {
//        return shopListDao.getShopItemList().map {
//            mapper.mapListDbModelToListEntity(it)
//        }
//    }


    override fun addShopItem(item: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(item))
    }

    override fun deleteShopItem(item: ShopItem) {
        shopListDao.deleteShopItem(item.id)
    }

    override fun editShopItem(item: ShopItem) {
        addShopItem(item)
    }

    override fun getShopItemById(id: Int): ShopItem {
        val shopItemDbModel = shopListDao.getShopItemById(id)
        return mapper.mapDbModelToEntity(shopItemDbModel)
    }


}