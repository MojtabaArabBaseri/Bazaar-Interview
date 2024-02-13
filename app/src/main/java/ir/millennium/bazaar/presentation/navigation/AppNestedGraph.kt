package ir.millennium.bazaar.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ir.millennium.bazaar.presentation.activity.MainActivityViewModel
import ir.millennium.bazaar.presentation.screens.mainScreen.MainScreen
import ir.millennium.bazaar.presentation.screens.mainScreen.MainScreenViewModel

fun NavGraphBuilder.appGraph(mainActivityViewModel: MainActivityViewModel) {

    navigation(startDestination = Screens.MainScreenRoute.route, route = Screens.AppRoute.route) {

        composable(route = Screens.MainScreenRoute.route,
            enterTransition = { slideInHorizontally(animationSpec = tween(700)) },
            exitTransition = { slideOutHorizontally(animationSpec = tween(700)) }) {
            val mainScreenViewModel = hiltViewModel<MainScreenViewModel>(it)
            MainScreen(mainScreenViewModel = mainScreenViewModel, mainActivityViewModel)
        }
    }
}