package com.example.shoppinglist.presentation.shopListScreen

sealed interface ShopListEffect {
    data class ShowSnackbar(val message: String) : ShopListEffect
    data class NavigateTo(val destination: String) : ShopListEffect
}