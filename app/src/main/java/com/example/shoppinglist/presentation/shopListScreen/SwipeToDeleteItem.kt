package com.example.shoppinglist.presentation.shopListScreen


import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SwipeToDeleteItem(
    item: T,
    onDelete: (T) -> Unit,
    content: @Composable () -> Unit,
) {

    val dismissState = rememberDismissState()

    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
        LaunchedEffect(key1 = item) {
            onDelete(item)
        }
    }

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        background = {},
        dismissContent = { content() }
    )
}