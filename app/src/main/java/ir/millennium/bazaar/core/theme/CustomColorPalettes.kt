package ir.millennium.bazaar.core.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColorsPalette(
    val textColorPrimary: Color = NavyColor,
    val iconColorPrimary: Color = NavyColor,
    val rippleColor: Color = White,
)

val LightCustomColorsPalette = CustomColorsPalette(
    textColorPrimary = NavyColor,
    iconColorPrimary = NavyColor,
    rippleColor = White,
)

val DarkCustomColorsPalette = CustomColorsPalette(
    textColorPrimary = White,
    iconColorPrimary = White,
    rippleColor = White,
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }