package com.example.shoppinglist.presentation.shopListScreen

import com.example.shoppinglist.domain.ShopItem

sealed interface ShopListIntent {
    data object LoadItems : ShopListIntent
    data object AddItem : ShopListIntent
    data class DeleteItem(val item: ShopItem) : ShopListIntent
    data class ToggleItemState(val item: ShopItem) : ShopListIntent
}