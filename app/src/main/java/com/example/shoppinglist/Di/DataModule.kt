package com.example.shoppinglist.Di

import android.app.Application
import com.example.shoppinglist.data.database.ShopListDao
import com.example.shoppinglist.data.database.ShoppingListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideShopListDao(application: Application): ShopListDao {
        return ShoppingListDatabase.getInstance(application).shopListDao()
    }

}