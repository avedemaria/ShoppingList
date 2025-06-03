package com.example.shoppinglist.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDbModel::class], version = 1, exportSchema = false)
abstract class ShoppingListDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: ShoppingListDatabase? = null
        private val DB_NAME = "shop_item.db"

        fun getInstance(application: Application): ShoppingListDatabase {

            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    application.applicationContext,
                    ShoppingListDatabase::class.java, DB_NAME
                ).build().also { INSTANCE = it }

            }
        }
    }

    abstract fun shopListDao(): ShopListDao

}
