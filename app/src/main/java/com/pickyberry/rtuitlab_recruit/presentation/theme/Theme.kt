package com.pickyberry.rtuitlab_recruit.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.pickyberry.rtuitlab_recruit.presentation.theme.AppShapes


private val DarkColorPalette = darkColors(
    primary = BackgroundBlue,
    secondary = AccentYellow,
    onPrimary = AlmostWhite,
    onBackground = LightBlue
)

private val LightColorPalette = lightColors(
    primary = BackgroundWhite,
    secondary = AccentRed,
    onPrimary = Color.Black,
    onBackground = Brown
)


@Composable
fun CoinsAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = Typography(),
        shapes = AppShapes,
        content = content
    )
}
