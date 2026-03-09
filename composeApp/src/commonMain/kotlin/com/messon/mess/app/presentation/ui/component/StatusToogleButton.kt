package com.messon.mess.app.presentation.ui.component
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.mess.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansmedium
import org.jetbrains.compose.resources.Font

@Composable
fun StatusToggleButton(
    modifier: Modifier,
    label: String,
    isSelected: Boolean,
    activeColor: Color,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(if (isSelected) activeColor else Color.Transparent)
    val textColor by animateColorAsState(if (isSelected) Color.White else CustomColors.BlackberryCream.copy(0.4f))
    Box(
        modifier =modifier
            .clip(CircleShape)
            .background(bgColor)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
            color = textColor
        )
    }
}