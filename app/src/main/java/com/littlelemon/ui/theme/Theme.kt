package com.littlelemon.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple700 = Color(0xFFEE9972)

private val DarkColorPalette = darkColors(
    primary = LittleLemonColor.yellow,
    primaryVariant = Purple700,
    secondary = LittleLemonColor.pink,
    background = LittleLemonColor.cloud
)

private val LightColorPalette = lightColors(
    primary = LittleLemonColor.yellow,
    primaryVariant = Purple700,
    secondary = LittleLemonColor.pink,
    background = LittleLemonColor.cloud
)

@Composable
fun LittleLemonTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
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