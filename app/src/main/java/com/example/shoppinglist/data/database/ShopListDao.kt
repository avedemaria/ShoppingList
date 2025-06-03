package com.example.shoppinglist.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopListDao {

    @Query("SELECT * from shop_items")
    fun getShopItemList(): Flow<List<ShopItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItemDbModel: ShopItemDbModel)

    @Query("DELETE from shop_items WHERE id=:id")
    suspend fun deleteShopItem(id: Int)

    @Query("SELECT * from shop_items WHERE id=:id LIMIT 1")
    suspend fun getShopItemById(id: Int): ShopItemDbModel
}