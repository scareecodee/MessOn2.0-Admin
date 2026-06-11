package com.messon.mess.app.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = MessonPalette.RichCerulean,
    secondary = MessonPalette.HoneyBronze,
    tertiary = MessonPalette.Teal,

    background = MessonPalette.OffWhite,
    surface = Color.White,

    onBackground = MessonPalette.BlackberryCream,
    onSurface = MessonPalette.BlackberryCream,

    outline = MessonPalette.LightGrayBlue
)

val DarkColorScheme = darkColorScheme(
    primary = MessonPalette.DarkRichCerulean,
    secondary = MessonPalette.DarkHoneyBronze,
    tertiary = MessonPalette.DarkTeal,

    background = MessonPalette.DarkOffWhite,
    surface = MessonPalette.DarkLightBlue,

    onBackground = MessonPalette.DarkBlackberryCream,
    onSurface = MessonPalette.DarkBlackberryCream,

    outline = MessonPalette.DarkLightGrayBlue
)