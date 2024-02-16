package ir.millennium.bazaar.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object CustomRippleTheme : RippleTheme {

    @Composable
    override fun defaultColor(): Color = LocalCustomColorsPalette.current.rippleColor

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        contentColor = LocalCustomColorsPalette.current.rippleColor,
        lightTheme = !isSystemInDarkTheme()
    )
}
