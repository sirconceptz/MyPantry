package com.hermanowicz.pantry.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryColor,
    secondary = Color.Gray,
    secondaryVariant = Color.White,
    onPrimary = Color.White,
    background = Color.Black,
    surface = GrayOnSurface
)

private val LightColorPalette = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryColor,
    secondary = Color.Gray,
    secondaryVariant = Color.Black,
    onPrimary = Color.Gray,
    background = Blue50,
    surface = Color.White,
    onSurface = Color.Black
)

@Composable
fun MyPantryTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setNavigationBarColor(
            color = PrimaryColor,
            darkIcons = false
        )
    }

    CompositionLocalProvider(LocalSpacing provides Spacing()) {}

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
