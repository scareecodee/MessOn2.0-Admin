package com.messon.mess.app.presentation.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.mess.app.domain.dataModel.DateItem
import com.messon.mess.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font


@Composable
fun DayDateChip(
    item: DateItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    accentColor: Color = CustomColors.RichCerulean,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) accentColor else Color.White,
        label = "bgColor"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else CustomColors.BlackberryCream,
        label = "textColor"
    )
    val dateCircleColor by animateColorAsState(
        targetValue = if (isSelected) Color.White.copy(alpha = 0.2f) else CustomColors.OffWhite,
        label = "circleColor"
    )
    val elevation by animateDpAsState(
        targetValue = if (isSelected) 8.dp else 0.dp,
        animationSpec = spring(),
        label = "elevation"
    )

    Surface(
        modifier = modifier
            .padding(vertical = 8.dp)
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(100),
                spotColor = accentColor.copy(alpha = 0.5f)
            )
            .clip(RoundedCornerShape(100))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = if (isSelected) accentColor else CustomColors.LightBlue,
                shape = RoundedCornerShape(100)
            )
            .clickable { onClick() },
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .padding(start = 6.dp, end = 16.dp, top = 6.dp, bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(dateCircleColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.formatedDate,
                    color = if (isSelected) Color.White else accentColor,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    letterSpacing = (-0.5).sp
                )
            }

            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = item.day.uppercase().take(3),
                    color = if (isSelected) Color.White.copy(alpha = 0.7f) else CustomColors.LightGrayBlue,
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                    letterSpacing = 1.sp
                )
                Text(
                    text = item.month,
                    color = textColor,
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                )
            }

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}


@Composable
fun PillSelectorStrip(
    dates: List<DateItem>,
    selectedId: String,
    onDateSelected: (String) -> Unit,
    accentColor: Color = CustomColors.RichCerulean
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(dates) { item ->
            DayDateChip(
                item = item,
                isSelected = item.id == selectedId,
                onClick = { onDateSelected(item.id) },
                accentColor = accentColor
            )
        }
    }
}
