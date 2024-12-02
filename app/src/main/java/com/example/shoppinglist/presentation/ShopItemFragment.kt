package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment(
    private val screenMode: String = MODE_UNKNOWN,
    private val shopItemId: Int = ShopItem.UNDEFINED_ID
) : Fragment() {

    private lateinit var saveButton: Button
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var viewModel: ShopItemViewModel


    override fun onCreateView(          //метод нужен для того чтобы из макета создать вью
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shop_item, container, false)
        return view
    }


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {                                                                        //вызывается когда вью точно будет создана, до вызова этого метода с вью нельзя работать
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        viewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        initFields(view)
        addTextChangeListeners()


        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }


        observeViewModel()

    }


    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItemLD.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }

        saveButton.setOnClickListener {
            viewModel.editShopItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun launchAddMode() {
        saveButton.setOnClickListener {
            viewModel.addShopItem(etName.text?.toString(), etCount.text?.toString())
        }
    }


    private fun observeViewModel() {

        viewModel.isErrorInputName.observe(viewLifecycleOwner) { //мы здесь передаем не this, а viewLifecycleOwner, тк view может "умереть" раньше фрагмента и будет краш из-за подписик на LiveData
            val message = if (it) {
                getString(R.string.error_invalid_name)
            } else {
                null
            }
            tilName.error = message
        }

        viewModel.isErrorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_invalid_count)
            } else {
                null
            }
            tilCount.error = message
        }

        viewModel.isScreenClosed.observe(viewLifecycleOwner) {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    private fun addTextChangeListeners() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })


        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }


    private fun parseParams() {
        if (screenMode != MODE_EDIT && screenMode != MODE_ADD) {
            throw RuntimeException("Param screen mode is absent")
        }

        if (screenMode == MODE_EDIT && shopItemId == ShopItem.UNDEFINED_ID) {
            throw RuntimeException("Param shopItemId is absent")
        }

    }

    private fun initFields(view: View) {
        saveButton = view.findViewById(R.id.save_button)
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"//это ключ для строк MODE_ADD и MODE_EDIT
        private const val MODE_ADD = "add_mode"
        private const val MODE_EDIT = "edit_mode"
        private const val MODE_UNKNOWN = ""
        private const val EXTRA_ID_ITEM = "item_id"


        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment(MODE_ADD)
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment(MODE_EDIT, shopItemId)
        }


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






