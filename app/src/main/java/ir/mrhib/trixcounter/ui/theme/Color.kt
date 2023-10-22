package ir.mrhib.trixcounter.ui.theme

import androidx.compose.ui.graphics.Color

sealed class ThemeColors(
    val bacground: Color,
    val surafce: Color,
    val primary: Color,
    val text: Color
) {
    object Night : ThemeColors(
        bacground = Color.Black,
        surafce = Color.Black,
        primary = Color.Black,
        text = Color(0xFF78FF42)
    )

    object Day : ThemeColors(
        bacground = Color.LightGray,
        surafce = Color.LightGray,
        primary = Color.LightGray,
        text = Color(0xFF227700)
    )
}