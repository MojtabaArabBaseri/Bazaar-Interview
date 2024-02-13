package ir.millennium.bazaar.presentation.screens.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ir.millennium.bazaar.R
import ir.millennium.bazaar.presentation.ui.theme.GrayDark
import ir.millennium.bazaar.presentation.ui.theme.LightNavyColor
import ir.millennium.bazaar.presentation.ui.theme.LocalCustomColorsPalette
import ir.millennium.bazaar.presentation.ui.theme.NavyColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun AlertWhenConnectionLostFrame(
    splashScreenViewModel: SplashScreenViewModel,
    navController: NavController,
    coroutineScope: CoroutineScope,
    isLoadingData: MutableState<Boolean>
) {

    Surface(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
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
                    if (splashScreenViewModel.auxiliaryFunctionsManager.isNetworkConnected()) {
                        coroutineScope.launch {
                            isLoadingData.value = true
                            delay(3000)
                            isLoadingData.value = false
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
}
