package ir.millennium.bazaar.presentation.utils

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import ir.millennium.bazaar.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
fun LazyGridState.OnBottomReached(
    onLoadMore: () -> Unit
) {
    val loadMore = remember {
        derivedStateOf {
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0)
            lastVisibleItemIndex == totalItemsNumber - 1 && totalItemsNumber != 0
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .filter { it }
            .collect {
                onLoadMore()
            }
    }
}

fun SnackbarHostState.showForExitApp(
    context: Context,
    coroutineScope: CoroutineScope
) {
    if (Constants.BACK_PRESSED + 2000 > System.currentTimeMillis()) {
        (context as? Activity)?.finish()
    } else {
        coroutineScope.launch { this@showForExitApp.showSnackbar(context.getString(R.string.message_when_user_exit_application)) }
    }
    Constants.BACK_PRESSED = System.currentTimeMillis()
}