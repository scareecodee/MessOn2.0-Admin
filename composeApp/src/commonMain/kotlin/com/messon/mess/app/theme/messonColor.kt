package com.messon.mess.app.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class MessonColors(
    val infoContainer: Color,
    val warningContainer: Color,
    val accentContainer: Color,
    val divider: Color,
    val success: Color
)

val LightMessonColors = MessonColors(
    infoContainer = MessonPalette.LightBlue,
    warningContainer = MessonPalette.Cream,
    accentContainer = MessonPalette.LightLavender,
    divider = MessonPalette.LightGrayBlue,
    success = MessonPalette.Teal
)

val DarkMessonColors = MessonColors(
    infoContainer = MessonPalette.DarkLightBlue,
    warningContainer = MessonPalette.DarkCream,
    accentContainer = MessonPalette.DarkLightLavender,
    divider = MessonPalette.DarkLightGrayBlue,
    success = MessonPalette.DarkTeal
)