package com.example.shoppinglist.presentation.addItemScreen

import com.example.shoppinglist.domain.ShopItem

data class AddShopItemState(
    val item: ShopItem? = null,
    val isNameError: Boolean = false,
    val isCountError: Boolean = false,
    val isLoading: Boolean = false
)