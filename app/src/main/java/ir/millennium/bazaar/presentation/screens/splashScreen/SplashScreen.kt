package ir.millennium.bazaar.presentation.screens.splashScreen

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ir.millennium.bazaar.R
import ir.millennium.bazaar.presentation.navigation.Screens
import ir.millennium.bazaar.presentation.ui.theme.GrayDark
import ir.millennium.bazaar.presentation.ui.theme.LightNavyColor
import ir.millennium.bazaar.presentation.ui.theme.LocalCustomColorsPalette
import ir.millennium.bazaar.presentation.ui.theme.NavyColor
import ir.millennium.bazaar.presentation.utils.Constants.BACK_PRESSED
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController, viewModel: SplashScreenViewModel) {

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(coroutineScope) {
        delay(3000)
        viewModel.isLoadingData.value = true
    }

    if (!viewModel.isLoadingData.value) {
        showLoading()
    } else {
        if (!viewModel.auxiliaryFunctionsManager.isNetworkConnected(context)) {
            showAlertWhenConnectionLost(viewModel, navController, coroutineScope)
        } else {
            navToMainScreen(navController)
        }
    }

    BackHandler { whenUserWantToExitApp(context, coroutineScope, snackbarHostState) }
}

@Composable
fun showLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(79.dp, 88.dp),
            painter = painterResource(id = R.mipmap.bazaar_logo),
            contentDescription = "Change Change Icon",
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.height(16.dp))

        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp,
            strokeCap = StrokeCap.Round,
        )
    }
}

@Composable
fun showAlertWhenConnectionLost(
    splashScreenViewModel: SplashScreenViewModel,
    navController: NavController,
    coroutineScope: CoroutineScope
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(96.dp, 96.dp)
                .padding(4.dp)
                .fillMaxSize()
                .aspectRatio(1f)
                .background(NavyColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_sad),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
        }

        Text(
            text = stringResource(id = R.string.connection_glitch),
            modifier = Modifier.padding(top = 4.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = LocalCustomColorsPalette.current.textColorPrimary,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = stringResource(id = R.string.connection_lost_message),
            modifier = Modifier.padding(top = 4.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            color = GrayDark,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (splashScreenViewModel.auxiliaryFunctionsManager.isNetworkConnected(context)) {
                    coroutineScope.launch {
                        splashScreenViewModel.isLoadingData.value = false
                        delay(3000)
                        splashScreenViewModel.isLoadingData.value = true
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = LightNavyColor)
        ) {
            Text(text = stringResource(id = R.string.retry))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navToMainScreen(navController) },
            colors = ButtonDefaults.buttonColors(containerColor = LightNavyColor)
        ) {
            Text(text = stringResource(id = R.string.load_from_database))
        }
    }
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