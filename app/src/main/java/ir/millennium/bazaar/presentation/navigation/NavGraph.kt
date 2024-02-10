package ir.millennium.bazaar.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ir.millennium.bazaar.presentation.activity.MainActivityViewModel

@Composable
fun NavGraph(navController: NavHostController, mainActivityViewModel: MainActivityViewModel) {

    NavHost(navController = navController, startDestination = Screens.AuthRoute.route) {

        authGraph(navController)

        appGraph(mainActivityViewModel)
    }
}