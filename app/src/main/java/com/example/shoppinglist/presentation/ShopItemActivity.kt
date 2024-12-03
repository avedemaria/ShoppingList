package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {


    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shop_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.shopItemContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        parseIntent()


        if (savedInstanceState == null) {  //если фрагмент не был создан
            val fragment = when (screenMode) {
                MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
                MODE_ADD -> ShopItemFragment.newInstanceAddItem()
                else -> throw RuntimeException("Unknown screen mode $screenMode")
            }


            supportFragmentManager.beginTransaction()         //установка фрагмента в контейнер
                .replace(R.id.shopItemContainer, fragment)
                .commit()

        }

    }


    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {                 //если в активити не передан ключ для режимов то выдаем ошибку, чтобы разрабы знали что исправлять
            throw RuntimeException("Param screen mode is absent")
        }

        val mode =
            intent.getStringExtra(EXTRA_SCREEN_MODE)        //если ключ есть проверяем есть ли нужные нам режимы
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }

        screenMode = mode                                           //

        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_ID_ITEM))
                throw RuntimeException("Param shopItemId is absent")
        }
        shopItemId = intent.getIntExtra(EXTRA_ID_ITEM, ShopItem.UNDEFINED_ID)
    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"//это ключ для строк MODE_ADD и MODE_EDIT
        private const val MODE_ADD = "add_mode"
        private const val MODE_EDIT = "edit_mode"
        private const val MODE_UNKNOWN = ""
        private const val EXTRA_ID_ITEM = "item_id"


        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(
                context,
                ShopItemActivity::class.java//когда добавляем заметку хотим получить в данной активити реализацию добавления из вьюмодели
                //здесь передаем нужное название для использования в when
            )
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, id: Int): Intent {
            val intent = Intent(
                context,
                ShopItemActivity::class.java
            )     //когда редактируем заметку передаем айди сущ заметки + нужное название для when
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_ID_ITEM, id)
            return intent
        }
    }
}