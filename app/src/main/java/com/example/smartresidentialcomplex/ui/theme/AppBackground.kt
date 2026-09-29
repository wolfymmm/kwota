package com.example.smartresidentialcomplex.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush

@Composable
fun AppBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val colors = AppTheme.colors

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        colors.backgroundCenter, // Теплий жовтий у світлій / глибокий графітовий у темній
                        colors.backgroundEdge    // Світло-бузковий у світлій / майже чорний у темній
                    ),
                    radius = 1010f
                )
            )
            .systemBarsPadding()
            .imePadding(),
        content = content
    )
}