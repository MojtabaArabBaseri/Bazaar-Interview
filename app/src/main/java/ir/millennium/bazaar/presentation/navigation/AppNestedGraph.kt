package ir.millennium.bazaar.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ir.millennium.bazaar.presentation.screens.mainScreen.MainScreen
import ir.millennium.bazaar.presentation.screens.mainScreen.MainScreenViewModel

fun NavGraphBuilder.appGraph(navController: NavController) {

    navigation(startDestination = Screens.MainScreenRoute.route, route = Screens.AppRoute.route) {

        composable(route = Screens.MainScreenRoute.route) {
            val mainActivityViewModel = hiltViewModel<MainScreenViewModel>(it)
            MainScreen(viewModel = mainActivityViewModel)
        }
    }
}