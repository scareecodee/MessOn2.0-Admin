package com.messon.mess.app.presentation.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import org.jetbrains.compose.resources.Font

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

@Composable
fun DaySelector(
    modifier: Modifier = Modifier,
    selectedDay: String,
    onDaySelected: (String) -> Unit,
) {
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    val colorScheme = MaterialTheme.colorScheme

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        days.forEach { day ->
            val isSelected = selectedDay == day

            val containerColor = if (isSelected) colorScheme.primary else colorScheme.surface
            val textColor by animateColorAsState(
                targetValue = if (isSelected) Color.White else MaterialTheme.colorScheme.onBackground,
                label = ""
            )
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = containerColor,
                shadowElevation = if (isSelected) 6.dp else 1.dp,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onDaySelected(day) }
            ) {
                Text(
                    text = day,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                    color = textColor,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                )
            }
        }
    }
}