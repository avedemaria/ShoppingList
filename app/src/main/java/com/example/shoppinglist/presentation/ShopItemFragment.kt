package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.presentation.ShopItemActivity.Companion
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment() : Fragment() {

    private lateinit var saveButton: Button
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var viewModel: ShopItemViewModel

    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("SHOPITEMFRAGMENT", "onCreate")
        super.onCreate(savedInstanceState)
        parseParams()
    }

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

        val args = requireArguments()

            if (!args.containsKey(EXTRA_SCREEN_MODE)) {                 //если в фрагмент не передан ключ для режимов то выдаем ошибку, чтобы разрабы знали что исправлять
                throw RuntimeException("Param screen mode is absent")
            }

            val mode = args.getString(EXTRA_SCREEN_MODE)        //если ключ есть проверяем есть ли нужные нам режимы
            if (mode != MODE_EDIT && mode !=MODE_ADD) {
                throw RuntimeException("Unknown screen mode $mode")
            }

            screenMode = mode

            if (screenMode == MODE_EDIT) {
                if (!args.containsKey(EXTRA_ID_ITEM))
                    throw RuntimeException("Param shopItemId is absent")
            }
            shopItemId = args.getInt(EXTRA_ID_ITEM, ShopItem.UNDEFINED_ID)

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
            return ShopItemFragment().apply {
                arguments = Bundle().apply {                   //передаем значения в Bundle через agruments
                    putString(EXTRA_SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {

            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_EDIT)
                    putInt(EXTRA_ID_ITEM, shopItemId)
                }
            }
        }
    }

}






