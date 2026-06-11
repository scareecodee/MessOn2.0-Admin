package com.messon.mess.app.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


private val LocalMessonColors =
    staticCompositionLocalOf<MessonColors> {
        error("No MessonColors provided")
    }

@Composable
fun MessonTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme)
            DarkColorScheme
        else
            LightColorScheme
    ) {
        CompositionLocalProvider(
            LocalMessonColors provides
                    if (darkTheme)
                        DarkMessonColors
                    else
                        LightMessonColors
        ) {
            content()
        }
    }
}

object MessonTheme {
    val colors: MessonColors
        @Composable
        get() = LocalMessonColors.current
}