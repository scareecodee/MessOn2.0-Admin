package com.messon.mess.app.presentation.ui.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BoxedIcon(
    modifier: Modifier,
    radius:Int,
    boxColor: Color,
    iconColor: Color,
    iconsSize: Dp,
    painter: Painter,
    contentDescription: String
){
    Box(
        contentAlignment = Alignment.Center
        , modifier =modifier.background(
            color = boxColor,
            shape = RoundedCornerShape(radius)
        )
            .padding(15.dp)
    ){
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.size(iconsSize),
            tint =iconColor
        )
    }
}