package com.example.smartresidentialcomplex.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

// Стандартна палітра Material 3 під ваш бренд
private val DarkColorScheme = darkColorScheme(
    primary = BrandPurpleDark,
    background = BgEdgeDark,
    surface = CardBgDark
)

private val LightColorScheme = lightColorScheme(
    primary = BrandPurpleLight,
    background = BgEdgeLight,
    surface = CardBgLight
)

@Composable
fun SmartResidentialComplexTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Обираємо відповідний набір кольорів із файлу Color.kt
    val customColors = if (darkTheme) DarkAppColors else LightAppColors
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(
        LocalAppColors provides customColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

// Зручний об'єкт для доступу до кольорів у будь-якій Composable-функції через: AppTheme.colors.<назва>
object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current
}