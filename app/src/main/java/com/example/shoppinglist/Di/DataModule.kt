package com.example.shoppinglist.Di

import android.app.Application
import com.example.shoppinglist.data.database.AppDataBase
import com.example.shoppinglist.data.database.ShopListDao
import dagger.Module
import dagger.Provides


@Module
class DataModule {

    @ApplicationScope
    @Provides
    fun provideShopListDao(application: Application): ShopListDao {
        return AppDataBase.getInstance(application).shopListDao()
    }

}