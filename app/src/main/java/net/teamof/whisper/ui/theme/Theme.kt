package net.teamof.whisper.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import net.teamof.whisper.R


val fontFamily = FontFamily(
    Font(R.font.acumin_pro_black, FontWeight.Black),
    Font(R.font.acumin_pro_bold, FontWeight.Bold),
    Font(R.font.acumin_pro_extralight, FontWeight.Light),
    Font(R.font.acumin_pro_medium, FontWeight.Medium),
    Font(R.font.acumin_pro_book, FontWeight.Normal)
)

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    onPrimary = Color(0xff4080fe),
    onSecondary = Color(red = 209, green = 213, blue = 219, alpha = 1),
)

private val LightColorPalette = lightColors(
    primary = Color(0xff0a5dfe),
    secondary = Color(0xff3ddc84),
    primaryVariant = Purple700,
    onPrimary = Color(0xff111827),
    onSecondary = Color(0xff6b7280),

    /* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/
)

@Composable
fun WhisperTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}