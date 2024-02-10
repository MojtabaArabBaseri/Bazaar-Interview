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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ir.millennium.bazaar.R
import ir.millennium.bazaar.data.dataSource.remote.UiState
import ir.millennium.bazaar.presentation.ui.theme.LocalCustomColorsPalette
import ir.millennium.bazaar.presentation.ui.theme.White
import ir.millennium.bazaar.presentation.utils.Constants.BACK_PRESSED
import ir.millennium.bazaar.presentation.utils.OnBottomReached
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainScreenViewModel = hiltViewModel()) {

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val swipeRefreshState = rememberSwipeRefreshState(false)

    val snackbarHostState = remember { SnackbarHostState() }

    Column(
        Modifier
            .background(White)
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
                IconButton(onClick = { }) {
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
                containerColor = LocalCustomColorsPalette.current.toolbarColor
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
                onRefresh = { viewModel.refresh() }
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(120.dp),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxSize(),
                    state = viewModel.stateLazyColumn,
                ) {
                    items(viewModel.movieList.size) { index ->
                        rowMovie(
                            movieItem = viewModel.movieList[index],
                            snackbarHostState,
                            coroutineScope
                        )
                    }

                    if (viewModel.isShowLoadingData.value && viewModel.movieList.isNotEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(16.dp)
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

    viewModel.stateLazyColumn.OnBottomReached {
        if (!viewModel.isShowLoadingData.value) {
            viewModel.isShowLoadingData(true)
            viewModel.getNextPage()
        }
    }

    renderUi(
        viewModel,
        coroutineScope,
        snackbarHostState,
        viewModel.isShowLoadingData,
        swipeRefreshState
    )

    getData(viewModel, coroutineScope)

    BackHandler { whenUserWantToExitApp(context, coroutineScope, snackbarHostState) }
}

@Composable
fun getData(viewModel: MainScreenViewModel, coroutineScope: CoroutineScope) {
    LaunchedEffect(coroutineScope) {
        if (viewModel.movieList.isEmpty()) {
            viewModel.getMovieList(viewModel.params)
        }
    }
}

@Composable
fun renderUi(
    viewModel: MainScreenViewModel,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    isShowLoadingData: StateFlow<Boolean>,
    swipeRefreshState: SwipeRefreshState
) {
    LaunchedEffect(coroutineScope) {
        viewModel.uiState.collect { dataResource ->
            when (dataResource) {
                is UiState.Loading -> {
                    if (!isShowLoadingData.value) {
                        swipeRefreshState.isRefreshing = true
                    }
                }

                is UiState.Success -> {
                    if (isShowLoadingData.value) {
                        viewModel.isShowLoadingData(false)
                    } else {
                        swipeRefreshState.isRefreshing = false
                    }
                }

                is UiState.Error -> {
                    if (swipeRefreshState.isRefreshing) {
                        swipeRefreshState.isRefreshing = false
                    }
                    coroutineScope.launch {
                        val snackbarResult = snackbarHostState.showSnackbar(
                            dataResource.throwable.message.toString(),
                            "retry"
                        )
                        when (snackbarResult) {
                            SnackbarResult.Dismissed -> {}
                            SnackbarResult.ActionPerformed -> {
                                viewModel.getMovieList(viewModel.params)
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }
}

fun whenUserWantToExitApp(
    context: Context,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    if (BACK_PRESSED + 2000 > System.currentTimeMillis()) {
        (context as? Activity)?.finish()
    } else {
        coroutineScope.launch { snackbarHostState.showSnackbar(context.getString(R.string.message_when_user_exit_application)) }
    }
    BACK_PRESSED = System.currentTimeMillis()
}