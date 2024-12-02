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

//    private lateinit var saveButton: Button
//    private lateinit var tilName: TextInputLayout
//    private lateinit var tilCount: TextInputLayout
//    private lateinit var etName: EditText
//    private lateinit var etCount: EditText
//    private lateinit var viewModel: ShopItemViewModel

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


        val fragment = when (screenMode) {
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }

        supportFragmentManager.beginTransaction()         //установка фрагмента в контейнер
            .add(R.id.shopItemContainer, fragment)
            .commit()


    }

//    private fun launchEditMode() {
//        viewModel.getShopItem(shopItemId)
//        viewModel.shopItemLD.observe(this) {
//            etName.setText(it.name)
//            etCount.setText(it.count.toString())
//        }
//
//        saveButton.setOnClickListener {
//            viewModel.editShopItem(etName.text?.toString(), etCount.text?.toString())
//        }
//    }
//
//    private fun launchAddMode() {
//        saveButton.setOnClickListener {
//            viewModel.addShopItem(etName.text?.toString(), etCount.text?.toString())
//        }
//    }
//
//
//    private fun observeViewModel() {
//
//        viewModel.isErrorInputName.observe(this) {
//            val message = if (it) {
//                getString(R.string.error_invalid_name)
//            } else {
//                null
//            }
//            tilName.error = message
//        }
//
//        viewModel.isErrorInputCount.observe(this) {
//            val message = if (it) {
//                getString(R.string.error_invalid_count)
//            } else {
//                null
//            }
//            tilCount.error = message
//        }
//
//        viewModel.isScreenClosed.observe(this) {
//            finish()
//        }
//    }
//
//    private fun addTextChangeListeners() {
//        etName.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                viewModel.resetErrorInputName()
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//
//            }
//        })
//
//
//        etCount.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                viewModel.resetErrorInputCount()
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//
//            }
//        })
//    }


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
//
//    private fun initFields() {
//        saveButton = findViewById(R.id.save_button)
//        tilName = findViewById(R.id.til_name)
//        tilCount = findViewById(R.id.til_count)
//        etName = findViewById(R.id.et_name)
//        etCount = findViewById(R.id.et_count)
//    }
//}

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"//это ключ для строк MODE_ADD и MODE_EDIT
        private const val MODE_ADD = "add_mode"
        private const val MODE_EDIT = "edit_mode"
        private const val MODE_UNKNOWN = ""
        private const val EXTRA_ID_ITEM = "item_id"


        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(
                context,
                ShopItemActivity::class.java
            )//когда добавляем заметку хотим получить в данной активити реализацию добавления из вьюмодели
            //здесь передаем нужное название для использования в when
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