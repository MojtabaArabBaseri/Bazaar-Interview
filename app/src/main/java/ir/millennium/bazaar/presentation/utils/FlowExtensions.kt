package ir.millennium.bazaar.presentation.utils

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.filter

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