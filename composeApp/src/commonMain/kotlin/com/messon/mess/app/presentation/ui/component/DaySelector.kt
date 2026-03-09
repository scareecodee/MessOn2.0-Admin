package com.messon.mess.app.presentation.ui.component

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.mess.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import org.jetbrains.compose.resources.Font

@Composable
fun DaySelector(
    modifier: Modifier,
    selectedDay: String,
    onDaySelected: (String) -> Unit,
){
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        days.forEach { day ->
            val isSelected = selectedDay == day
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (isSelected) CustomColors.RichCerulean.copy(0.9f) else Color.White,
                shadowElevation = if (isSelected) 4.dp else 1.dp,
                modifier = Modifier.clip(RoundedCornerShape(16.dp))
                    .clickable { onDaySelected(day) }
            ) {
                Text(
                    text = day,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    color = if (isSelected) Color.White else CustomColors.BlackberryCream,
                    fontSize =10.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                )
            }
        }
    }
}