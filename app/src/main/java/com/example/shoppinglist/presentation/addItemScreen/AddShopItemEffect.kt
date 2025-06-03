package com.example.shoppinglist.presentation.addItemScreen

sealed interface AddShopItemEffect {
    data object NavigateBack : AddShopItemEffect
    data class ShowSnackbar(val message: String) : AddShopItemEffect
}