package ir.millennium.bazaar.core.theme

import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.millennium.bazaar.core.theme.enums.TypeTheme

private val DarkColorPalette = darkColorScheme(
    primary = Blue,
    onPrimary = White,
    surface = GrayVeryDark,
    onSurface = White,
    background = Black,
    onBackground = White,
    primaryContainer = Black,
    secondary = Green,
)

private val LightColorPalette = lightColorScheme(
    primary = Blue,
    onPrimary = White,
    surface = White,
    onSurface = Black,
    background = White,
    onBackground = Black,
    primaryContainer = White,
    secondary = Red,
    surfaceContainer = White
)

@Composable
fun BazaarTheme(
    typeTheme: Int = TypeTheme.LIGHT.typeTheme,
    content: @Composable () -> Unit
) {
    val colors = if (typeTheme == TypeTheme.DARK.typeTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val customColorsPalette =
        if (typeTheme == TypeTheme.DARK.typeTheme) {
            DarkCustomColorsPalette
        } else {
            LightCustomColorsPalette
        }

    if (typeTheme == TypeTheme.DARK.typeTheme) {
        rememberSystemUiController().setSystemBarsColor(color = Black)
        rememberSystemUiController().setNavigationBarColor(color = Black)
    } else {
        rememberSystemUiController().setSystemBarsColor(color = White)
        rememberSystemUiController().setNavigationBarColor(color = White)
    }

    CompositionLocalProvider(
        LocalCustomColorsPalette provides customColorsPalette
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = TypographyEnglish,
            shapes = Shapes()
        ) {
            CompositionLocalProvider(
                LocalRippleTheme provides CustomRippleTheme,
                content = content
            )
        }
    }
}
