package com.chefsphere

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun PeacockTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors(
            primary = Color(0xFF1e7074),
            secondary = Color(0xFF4fd1c7),
            background = Color(0xFFe6fffa),
            surface = Color.White,
            error = Color(0xFFdc2626),
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color(0xFF0f5257),
            onSurface = Color(0xFF1e7074),
            onError = Color.White
        ),
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}
