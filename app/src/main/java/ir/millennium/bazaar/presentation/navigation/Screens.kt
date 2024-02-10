package ir.millennium.bazaar.presentation.navigation

sealed class Screens(val route: String) {

    object SplashScreenRoute : Screens(route = "SplashScreen")

    object MainScreenRoute : Screens(route = "MainScreen")

    object AuthRoute : Screens(route = "Auth")

    object AppRoute : Screens(route = "App")
}
