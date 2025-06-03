package com.example.shoppinglist.presentation.shopListScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.shoppinglist.R
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ShopListScreen(
    state: ShopListState,
    onIntent: (ShopListIntent) -> Unit,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    viewModel: ShopListViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
                is ShopListEffect.NavigateTo -> navController.navigate(effect.destination)
                is ShopListEffect.ShowSnackbar -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onIntent(ShopListIntent.AddItem) },
                containerColor = Color(ContextCompat.getColor(context, R.color.purple))
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add item")
            }
        },
    ) { padding ->

        when (state) {
            is ShopListState.Empty -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Список пуст", fontSize = 18.sp)
            }

            is ShopListState.Error -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Ошибка загрузки", fontSize = 18.sp)
            }

            is ShopListState.Loading -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            )
            {
                CircularProgressIndicator()
            }

            is ShopListState.Success -> {
                LazyColumn(modifier = Modifier.padding(padding)) {
                    items(items = state.items, key = {it.id}) { item ->
                        SwipeToDeleteItem(
                            item = item,
                            onDelete = { onIntent(ShopListIntent.DeleteItem(item)) }
                        ) {
                            ShopListItem(item = item,
                                onLongClick = { onIntent(ShopListIntent.ToggleItemState(item)) })
                        }
                    }

                }
            }
        }
    }
}



