package ir.millennium.bazaar.presentation.screens.splashScreen

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import ir.millennium.bazaar.R
import ir.millennium.bazaar.presentation.navigation.Screens
import ir.millennium.bazaar.presentation.utils.Constants.BACK_PRESSED
import ir.millennium.bazaar.presentation.utils.Constants.SPLASH_DISPLAY_LENGTH
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController, viewModel: SplashScreenViewModel) {

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    val isLoadingData = rememberSaveable { mutableStateOf(true) }

    LoadingFrame()

    LaunchedEffect(coroutineScope) {
        delay(SPLASH_DISPLAY_LENGTH)
        isLoadingData.value = false
    }

    if (isLoadingData.value) {
        LoadingFrame()
    } else {
        if (viewModel.auxiliaryFunctionsManager.isNetworkConnected()) {
            navToMainScreen(navController)
        } else {
            AlertWhenConnectionLostFrame(viewModel, navController, coroutineScope, isLoadingData)
        }
    }

    BackHandler { whenUserWantToExitApp(context, coroutineScope, snackbarHostState) }
}

fun navToMainScreen(navController: NavController) {
    navController.navigate(Screens.MainScreenRoute.route) {
        popUpTo(Screens.SplashScreenRoute.route) { inclusive = true }
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