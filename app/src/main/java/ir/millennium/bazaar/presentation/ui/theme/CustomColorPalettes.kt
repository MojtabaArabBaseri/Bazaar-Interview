package ir.millennium.bazaar.presentation.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColorsPalette(
    val textColorPrimary: Color = NavyColor,
    val iconColorPrimary: Color = NavyColor,
    val supplementTextColorPrimary: Color = White,
    val toolbarColor: Color = ToolbarLightColor,
    val rowSocialNetworkBackground: Color = BackgroundRowSocialNetwork,
    val rippleColor: Color = White,
    val navigationBottomColor: Color = NavigationBottomLightColor,
)

val LightCustomColorsPalette = CustomColorsPalette(
    textColorPrimary = NavyColor,
    iconColorPrimary = NavyColor,
    supplementTextColorPrimary = White,
    toolbarColor = ToolbarLightColor,
    rippleColor = White,
    rowSocialNetworkBackground = BackgroundRowSocialNetwork,
    navigationBottomColor = NavigationBottomLightColor
)

val DarkCustomColorsPalette = CustomColorsPalette(
    textColorPrimary = White,
    iconColorPrimary = White,
    supplementTextColorPrimary = NavyColor,
    toolbarColor = ToolbarDarkColor,
    rippleColor = White,
    rowSocialNetworkBackground = Black,
    navigationBottomColor = NavigationBottomDarkColor
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }