package com.example.shoppinglist.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemByIdUseCase
import com.example.shoppinglist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ShopItemViewModel (application: Application): AndroidViewModel(application) {

    private val shopListRepositoryImpl = ShopListRepositoryImpl(application)

    private val getShopItemByIdUseCase = GetShopItemByIdUseCase(shopListRepositoryImpl)
    private val editShopItemUseCase = EditShopItemUseCase(shopListRepositoryImpl)
    private val addShopItemUseCase = AddShopItemUseCase(shopListRepositoryImpl)


    private val _isErrorInputName = MutableLiveData<Boolean>()
    val isErrorInputName: LiveData<Boolean> get() = _isErrorInputName

    private val _isErrorInputCount = MutableLiveData<Boolean>()
    val isErrorInputCount: LiveData<Boolean> get() = _isErrorInputCount

    private val _shopItemLD = MutableLiveData<ShopItem>()
    val shopItemLD: LiveData<ShopItem> get() = _shopItemLD

    private val _isScreenClosed = MutableLiveData<Boolean>()
    val isScreenClosed: LiveData<Boolean> get() = _isScreenClosed

    private val scope = CoroutineScope(Dispatchers.IO)


    fun getShopItem(id: Int) {
        scope.launch {
            val item = getShopItemByIdUseCase.getShopItemById(id)
            _shopItemLD.value = item
        }

    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        val fieldValid = validateInput(name, count)
        if (fieldValid) {
            scope.launch {
                val shopItem = ShopItem(name, count, true)
                addShopItemUseCase.addShopItem(shopItem)
                _isScreenClosed.value = true
            }
        }

    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)

        val count = parseCount(inputCount)

        val fieldValid = validateInput(name, count)
        if (fieldValid) {
            _shopItemLD.value?.let {
                scope.launch {
                    val item = it.copy(name = name, count = count)
                    editShopItemUseCase.editShopItem(item)
                    _isScreenClosed.value = true
                }
            }


        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseCount(count: String?): Int {
        return try {
            count?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true

        if (name.isBlank()) {
            _isErrorInputName.value = true
            result = false
        }

        if (count <= 0) {
            _isErrorInputCount.value = true
            result = false
        }
        return result
    }


    fun resetErrorInputName() {
        _isErrorInputName.value = false
    }

    fun resetErrorInputCount() {
        _isErrorInputCount.value = false
    }
}