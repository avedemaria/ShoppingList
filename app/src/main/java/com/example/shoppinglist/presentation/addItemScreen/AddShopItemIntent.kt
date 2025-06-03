package com.example.shoppinglist.presentation.addItemScreen

sealed interface AddShopItemIntent {
    data class AddItem(val name: String?, val count: String?) : AddShopItemIntent
    data object ResetNameError : AddShopItemIntent
    data object ResetCountError : AddShopItemIntent
}