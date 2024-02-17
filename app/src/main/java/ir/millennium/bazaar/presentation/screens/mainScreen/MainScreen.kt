package ir.millennium.bazaar.presentation.screens.mainScreen

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ir.millennium.bazaar.R
import ir.millennium.bazaar.core.theme.LocalCustomColorsPalette
import ir.millennium.bazaar.core.theme.enums.TypeTheme
import ir.millennium.bazaar.data.model.remote.MovieItem
import ir.millennium.bazaar.presentation.activity.MainActivityViewModel
import ir.millennium.bazaar.presentation.utils.showForExitApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel,
    mainActivityViewModel: MainActivityViewModel
) {
    val movieList = mainScreenViewModel.moviePagingFlow.collectAsLazyPagingItems()

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val swipeRefreshState = rememberSwipeRefreshState(false)

    val snackbarHostState = remember { SnackbarHostState() }

    Column(
        Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            windowInsets = WindowInsets(top = 0, bottom = 0),
            title = {
                Text(
                    text = stringResource(id = R.string.discover),
                    color = LocalCustomColorsPalette.current.textColorPrimary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    coroutineScope.launch {
                        val statusTheme = mainActivityViewModel.statusThemeFlow.first()
                        if (statusTheme == TypeTheme.DARK.typeTheme) {
                            mainActivityViewModel.onThemeChanged(TypeTheme.LIGHT.typeTheme)
                        } else {
                            mainActivityViewModel.onThemeChanged(TypeTheme.DARK.typeTheme)
                        }
                        (context as? Activity)?.recreate()
                    }
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_change_theme),
                        contentDescription = "Change Theme Icon",
                        tint = LocalCustomColorsPalette.current.iconColorPrimary
                    )
                }
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.mipmap.bazaar_logo),
                        contentDescription = "Change Change Icon",
                        tint = Color.Unspecified
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )

        Box(modifier = Modifier.fillMaxSize()) {
            SwipeRefresh(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                state = swipeRefreshState,
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = trigger,
                        scale = true,
                        backgroundColor = MaterialTheme.colorScheme.onBackground,
                        contentColor = MaterialTheme.colorScheme.background
                    )
                },
                onRefresh = { movieList.refresh() }
            ) {

                if (movieList.loadState.refresh is LoadState.Loading) {
                    swipeRefreshState.isRefreshing = true
                } else {
                    swipeRefreshState.isRefreshing = false
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(120.dp),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxSize(),
                        state = mainScreenViewModel.stateLazyColumn,
                    ) {
                        items(movieList.itemCount) { index ->
                            RowMovie(
                                movieItem = movieList[index]!!,
                                snackbarHostState,
                                coroutineScope
                            )
                        }

                        item {
                            if (movieList.loadState.append is LoadState.Loading) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(190.dp)
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .align(Alignment.Center),
                                        color = MaterialTheme.colorScheme.primary,
                                        strokeWidth = 2.dp
                                    )
                                }
                            }
                        }
                    }
                }

                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }

    LaunchedEffect(key1 = movieList.loadState) {
        if (movieList.loadState.refresh is LoadState.Error) {
            handleError(
                context,
                snackbarHostState,
                coroutineScope,
                (movieList.loadState.refresh as LoadState.Error).error.message!!,
                movieList
            )
        }

        if (movieList.loadState.append is LoadState.Error) {
            handleError(
                context,
                snackbarHostState,
                coroutineScope,
                (movieList.loadState.append as LoadState.Error).error.message!!,
                movieList
            )
        }
    }

    BackHandler { snackbarHostState.showForExitApp(context, coroutineScope) }
}

fun handleError(
    context: Context,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    message: String,
    movieList: LazyPagingItems<MovieItem>
) {
    coroutineScope.launch {
        when (snackbarHostState.showSnackbar(message, context.getString(R.string.retry))) {
            SnackbarResult.Dismissed -> {}
            SnackbarResult.ActionPerformed -> {
                movieList.retry()
            }
        }
    }
}
