package com.example.shoppinglist.presentation.addItemScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.ShopItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddShopItemViewModel @Inject constructor(
    private val addShopItemUseCase: AddShopItemUseCase
) : ViewModel() {


    private val intentChannel = Channel<AddShopItemIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow(AddShopItemState())
    val state: StateFlow<AddShopItemState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AddShopItemEffect>()
    val effect = _effect.asSharedFlow()


    init {
        handleIntents()
    }


    fun sendIntent(intent: AddShopItemIntent) {
        intentChannel.trySend(intent)
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is AddShopItemIntent.AddItem -> addShopItem(intent.name, intent.count)
                    is AddShopItemIntent.ResetNameError -> _state.update { it.copy(isNameError = false) }
                    is AddShopItemIntent.ResetCountError -> _state.update { it.copy(isCountError = false) }
                }
            }
        }
    }


    private fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        val fieldValid = validateInput(name, count)
        if (fieldValid) {
            viewModelScope.launch {
                addShopItemUseCase.addShopItem(
                    ShopItem(name, count, true)
                )
                _effect.emit(AddShopItemEffect.NavigateBack)
            }
        }
    }


    private fun parseName(name: String?): String = name?.trim() ?: ""

    private fun parseCount(count: String?): Int = count?.toIntOrNull() ?: 0

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true

        if (name.isBlank()) {
            _state.update { it.copy(isNameError = true) }
            result = false
        }

        if (count <= 0) {
            _state.update { it.copy(isCountError = true) }
            result = false
        }
        return result
    }

}


