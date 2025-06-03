package com.example.shoppinglist.presentation.shopListScreen

import com.example.shoppinglist.domain.ShopItem

sealed interface ShopListState {
    data object Loading : ShopListState
    data object Empty : ShopListState
    data object Error : ShopListState
    data class Success(val items: List<ShopItem>) : ShopListState
}