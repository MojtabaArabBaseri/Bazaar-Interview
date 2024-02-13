package ir.millennium.bazaar.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ir.millennium.bazaar.presentation.screens.splashScreen.SplashScreen
import ir.millennium.bazaar.presentation.screens.splashScreen.SplashScreenViewModel

fun NavGraphBuilder.authGraph(navController: NavController) {

    navigation(
        startDestination = Screens.SplashScreenRoute.route,
        route = Screens.AuthRoute.route
    ) {

        composable(route = Screens.SplashScreenRoute.route,
            enterTransition = { slideInHorizontally(animationSpec = tween(700)) },
            exitTransition = { slideOutHorizontally(animationSpec = tween(700)) }) {
            val splashScreenViewModel = hiltViewModel<SplashScreenViewModel>(it)
            SplashScreen(
                navController = navController,
                viewModel = splashScreenViewModel
            )
        }
    }
}