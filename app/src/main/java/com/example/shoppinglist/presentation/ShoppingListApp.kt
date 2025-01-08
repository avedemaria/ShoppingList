package com.example.shoppinglist.presentation

import android.app.Application
import com.example.shoppinglist.Di.DaggerAppComponent

class ShoppingListApp: Application() {


    val component by lazy {
     DaggerAppComponent.factory().create(this)
    }


}