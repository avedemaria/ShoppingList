package com.example.shoppinglist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.presentation.addItemScreen.AddItemScreen
import com.example.shoppinglist.presentation.addItemScreen.AddShopItemViewModel
import com.example.shoppinglist.presentation.shopListScreen.ShopListScreen
import com.example.shoppinglist.presentation.shopListScreen.ShopListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            setContent {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                NavHost(
                    navController = navController,
                    startDestination = "shop_list"
                ) {
                    composable("shop_list") {
                        val viewModel: ShopListViewModel = hiltViewModel()
                        val state by viewModel.shopListState.collectAsState()

                        ShopListScreen(
                            state = state,
                            onIntent = { viewModel.sendIntent(it) },
                            snackbarHostState = snackbarHostState,
                            navController = navController,
                            viewModel = viewModel
                        )
                    }

                    composable("add_item") {
                        val viewModel: AddShopItemViewModel = hiltViewModel()
                        val state by viewModel.state.collectAsState()

                        AddItemScreen(
                            state = state,
                            onIntent = {viewModel.sendIntent(it)},
                            snackbarHostState = snackbarHostState,
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }



