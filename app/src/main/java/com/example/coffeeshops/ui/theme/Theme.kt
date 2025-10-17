package com.example.coffeeshops.ui.theme


import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary   = Primary,
    secondary = Secondary,
    tertiary  = Tertiary,
    error     = ErrorRed,
    background = Neutral10,
    surface = Neutral10,
)

private val LightColorScheme = lightColorScheme(
    primary   = Primary,
    secondary = Secondary,
    tertiary  = Tertiary,
    error     = ErrorRed,
    background = Neutral98,
    surface = Neutral98,
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}