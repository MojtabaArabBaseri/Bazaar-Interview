package ir.millennium.bazaar.presentation.screens.splashScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import ir.millennium.bazaar.presentation.navigation.Screens
import ir.millennium.bazaar.presentation.utils.Constants.SPLASH_DISPLAY_LENGTH
import ir.millennium.bazaar.presentation.utils.showForExitApp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, viewModel: SplashScreenViewModel) {

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    val isLoadingData = rememberSaveable { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {

        if (isLoadingData.value) {
            LoadingFrame()
        } else {
            if (viewModel.auxiliaryFunctionsManager.isNetworkConnected()) {
                LoadingFrame()
                navToMainScreen(navController)
            } else {
                AlertWhenConnectionLostFrame(
                    viewModel,
                    navController,
                    coroutineScope,
                    isLoadingData
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    LaunchedEffect(coroutineScope) {
        delay(SPLASH_DISPLAY_LENGTH)
        isLoadingData.value = false
    }

    BackHandler { snackbarHostState.showForExitApp(context, coroutineScope) }
}

fun navToMainScreen(navController: NavController) {
    navController.navigate(Screens.MainScreenRoute.route) {
        popUpTo(Screens.SplashScreenRoute.route) { inclusive = true }
    }
}