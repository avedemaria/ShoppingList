package com.example.shoppinglist.presentation.shopListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.domain.DeleteShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemListUseCase
import com.example.shoppinglist.domain.ShopItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopListViewModel @Inject constructor(
    private val getShopListUseCase: GetShopItemListUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase
) : ViewModel() {


    private val _shopListState = MutableStateFlow<ShopListState>(ShopListState.Loading)
    val shopListState: StateFlow<ShopListState> = _shopListState.asStateFlow()

    private val intentChannel = Channel<ShopListIntent>(Channel.UNLIMITED)

    private val _uiEffect = MutableSharedFlow<ShopListEffect>()
    val uiEffect = _uiEffect.asSharedFlow()


    init {
        handleIntents()
        sendIntent(ShopListIntent.LoadItems)
    }

    fun sendIntent(intent: ShopListIntent) {
        intentChannel.trySend(intent)
    }

   private fun handleIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is ShopListIntent.LoadItems -> getShopItems()
                    is ShopListIntent.DeleteItem -> {
                        deleteShopItem(intent.item)
                        _uiEffect.emit(ShopListEffect.ShowSnackbar("Элемент удалён"))
                    }

                    is ShopListIntent.ToggleItemState -> changeEnableState(intent.item)
                    is ShopListIntent.AddItem -> {
                        _uiEffect.emit(ShopListEffect.NavigateTo("add_item"))
                    }
                }
            }
        }
    }

    private fun getShopItems() {
        viewModelScope.launch {
            getShopListUseCase.getShopItemList()
                .onStart {_shopListState.value = ShopListState.Loading }
                .catch { e ->
                    _shopListState.value = ShopListState.Error
                    _uiEffect.emit(ShopListEffect.ShowSnackbar(e.message ?: "Неизвестная ошибка"))
                }
                .collect { list ->
                    _shopListState.value = when {
                        list.isEmpty() -> ShopListState.Empty
                        else -> ShopListState.Success(list)
                    }
                }
        }
    }


   private fun deleteShopItem(item: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(item)
        }

    }

    private fun changeEnableState(item: ShopItem) {
        viewModelScope.launch {
            val newItem = item.copy(isEnabled = !item.isEnabled)
            editShopItemUseCase.editShopItem(newItem)
        }

    }


}