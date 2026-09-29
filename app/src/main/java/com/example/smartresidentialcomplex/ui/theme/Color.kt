package com.example.smartresidentialcomplex.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Базові сирі кольори
val BrandPurpleLight = Color(0xFF9E7BB5)
val BrandPurpleDark = Color(0xFFBB9AD8)

val BgCenterLight = Color(0xFFFFE9B2)
val BgEdgeLight = Color(0xFFFDF8FF)

val BgCenterDark = Color(0xFF141316)
val BgEdgeDark = Color(0xFF0A0A0C)

val CardBgLight = Color(0xFFFCF8F5).copy(alpha = 0.85f)
val CardBgDark = Color(0xFF1E1E22).copy(alpha = 0.92f)

// Структура токенів теми
data class AppColors(
    val isDark: Boolean,
    val backgroundCenter: Color,
    val backgroundEdge: Color,
    val cardBackground: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val sectionHeader: Color,
    val brandPurple: Color,
    val navBarBackground: Color,
    val navBarInactive: Color,
    val navBarActive: Color,
    val actionButtonBg: Color,
    val glowStart: Color,
    val glowEnd: Color
)

// Світла палітра
val LightAppColors = AppColors(
    isDark = false,
    backgroundCenter = BgCenterLight,
    backgroundEdge = BgEdgeLight,
    cardBackground = CardBgLight,
    textPrimary = Color(0xFF1E232C),
    textSecondary = Color(0xFF9E8EAA),
    textMuted = Color(0xFFB4A8BA),
    sectionHeader = Color(0xFF2C2D2E),
    brandPurple = BrandPurpleLight,
    navBarBackground = Color.White,
    navBarInactive = Color(0xFF4A4B4D),
    navBarActive = BrandPurpleLight,
    actionButtonBg = Color.White.copy(alpha = 0.9f),
    glowStart = Color(0xFFF7EFFB).copy(alpha = 0.75f),
    glowEnd = Color(0xFFF3E7F8)
)

// Темна палітра
val DarkAppColors = AppColors(
    isDark = true,
    backgroundCenter = BgCenterDark,
    backgroundEdge = BgEdgeDark,
    cardBackground = CardBgDark,
    textPrimary = Color(0xFFF5F5F7),
    textSecondary = Color(0xFFB5A9C0),
    textMuted = Color(0xFF75747B),
    sectionHeader = Color(0xFFDDD7E3),
    brandPurple = BrandPurpleDark,
    navBarBackground = Color(0xFF121214),
    navBarInactive = Color(0xFF86848C),
    navBarActive = BrandPurpleDark,
    actionButtonBg = Color(0xFF27262B),
    glowStart = Color(0xFF211D25).copy(alpha = 0.8f),
    glowEnd = Color(0xFF141317)
)

val LocalAppColors = staticCompositionLocalOf { LightAppColors }