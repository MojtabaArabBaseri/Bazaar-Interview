package ir.millennium.bazaar.presentation.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.millennium.bazaar.core.ui.BaseActivity
import ir.millennium.bazaar.presentation.navigation.NavGraph
import ir.millennium.bazaar.presentation.ui.theme.BazaarTheme

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val mainActivityViewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()
            BazaarTheme(typeTheme = mainActivityViewModel.typeTheme.value) {
                Surface(
                    modifier = Modifier
                        .statusBarsPadding()
                        .navigationBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(navController, mainActivityViewModel)
                }
            }
        }
    }
}