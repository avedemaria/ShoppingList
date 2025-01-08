package com.example.shoppinglist.Di

import com.example.shoppinglist.data.repository.ShopListRepositoryImpl
import com.example.shoppinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module


@Module
interface DomainModule {

    @ApplicationScope
    @Binds
    fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository

}