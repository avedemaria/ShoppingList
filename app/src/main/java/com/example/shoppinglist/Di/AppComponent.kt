package com.example.shoppinglist.Di

import android.app.Application
import com.example.shoppinglist.data.database.AppDataBase
import com.example.shoppinglist.presentation.MainActivity
import com.example.shoppinglist.presentation.ShopItemActivity
import com.example.shoppinglist.presentation.ShopItemFragment
import com.example.shoppinglist.presentation.ShoppingListApp
import dagger.BindsInstance
import dagger.Component


@ApplicationScope
@Component(modules = [DataModule::class, DomainModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: ShopItemActivity)

    fun inject(fragment: ShopItemFragment)

    fun inject(application: ShoppingListApp)


    @Component.Factory
    interface AppComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }

}