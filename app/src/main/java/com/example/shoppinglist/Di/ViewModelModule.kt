package com.example.shoppinglist.Di

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.presentation.MainViewModel
import com.example.shoppinglist.presentation.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule {


    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    @Binds
    fun bindShopItemViewModel(impl: ShopItemViewModel): ViewModel


    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(impl: MainViewModel): ViewModel

}