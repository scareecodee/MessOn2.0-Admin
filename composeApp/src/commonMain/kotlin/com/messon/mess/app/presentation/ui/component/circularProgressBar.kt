package com.messon.mess.app.presentation.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import messon.composeapp.generated.resources.publicsansmedium
import org.jetbrains.compose.resources.Font
import kotlin.math.absoluteValue


@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    percentage: Float,
    radius: Dp,
    color: Color,
    strokeWidth: Dp,
    animationDuration: Int,
    animationDelay: Int,
    numbStudents: Int,
    showTrack: Boolean = true,
    mealSlot: String,
    ) {
    var isAnimationPlayed by remember { mutableStateOf(false) }

    val currentPercentage by animateFloatAsState(
        targetValue = if (isAnimationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        )
    )

    val animatedValue by animateIntAsState(
        targetValue = if (isAnimationPlayed) numbStudents else 0,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        )
    )

    LaunchedEffect(Unit) {
        isAnimationPlayed = true
    }

    val trackColor = color.copy(alpha = 0.1f)

    val canvasSize = radius * 2f + strokeWidth

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = modifier.width(canvasSize)
            .height(canvasSize+32.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,

            ) {
            Canvas(
                modifier = Modifier.size(canvasSize)
            ) {
                if (showTrack) {
                    drawArc(
                        color = trackColor,
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                    )
                }

                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = currentPercentage * 360,
                    useCenter = false,
                    style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }
            Text(
                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                text = animatedValue.absoluteValue.toString(),
                fontSize = 15.sp,
            )
        }

        Text(
            maxLines = 1,
            text =mealSlot,
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansmedium))
        )
    }

}