package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppinglist.domain.ShopItem

@Dao
interface ShopListDao {


    @Query("SELECT * from shop_items")
    fun getShopItemList(): LiveData<List<ShopItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)//эта строка означает, что если в нашу таблицу добавится элементс тем же айди, то он перезапишется в таблицу
    fun addShopItem(shopItemDbModel: ShopItemDbModel)

    @Query("DELETE from shop_items WHERE id=:id")
    fun deleteShopItem(id:Int)

    @Query("SELECT * from shop_items WHERE id=:id LIMIT 1")
    fun getShopItemById (id:Int)
}