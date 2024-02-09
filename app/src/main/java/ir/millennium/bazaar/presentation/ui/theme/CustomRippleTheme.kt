package ir.millennium.bazaar.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ir.millennium.bazaar.presentation.ui.theme.LocalCustomColorsPalette

object CustomRippleTheme : RippleTheme {

    @Composable
    override fun defaultColor(): Color = LocalCustomColorsPalette.current.rippleColor

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        contentColor = LocalCustomColorsPalette.current.rippleColor,
        lightTheme = !isSystemInDarkTheme()
    )
}
