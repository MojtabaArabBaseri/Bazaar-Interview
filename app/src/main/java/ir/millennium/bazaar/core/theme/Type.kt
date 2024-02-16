package ir.millennium.bazaar.core.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ir.millennium.bazaar.R

object AppFont {
    val FontEnglish = FontFamily(
        Font(R.font.dana_en_num_regular, FontWeight.Light),
        Font(R.font.dana_en_num_regular, FontWeight.Normal),
        Font(R.font.dana_en_num_regular, FontWeight.Medium),
        Font(R.font.dana_en_num_bold, FontWeight.Bold),
        Font(R.font.dana_en_num_bold, FontWeight.ExtraBold)
    )
}

val TypographyEnglish = androidx.compose.material3.Typography(
    titleLarge = TextStyle(
        fontFamily = AppFont.FontEnglish,
        fontSize = 20.sp,
        lineHeight = 24.0.sp,
        letterSpacing = 0.2.sp
    ),

    titleMedium = TextStyle(
        fontFamily = AppFont.FontEnglish,
        fontSize = 16.sp,
        lineHeight = 24.0.sp,
        letterSpacing = 0.2.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = AppFont.FontEnglish,
        fontSize = 14.sp,
        lineHeight = 20.0.sp,
        letterSpacing = 0.2.sp
    ),
    bodySmall = TextStyle(
        fontFamily = AppFont.FontEnglish,
        fontSize = 12.sp,
        lineHeight = 16.0.sp,
        letterSpacing = 0.4.sp
    ),
)