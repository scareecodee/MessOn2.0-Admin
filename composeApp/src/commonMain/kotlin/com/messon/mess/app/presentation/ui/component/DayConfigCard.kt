package com.messon.mess.app.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.mess.app.theme.CustomColors
import com.messon.mess.app.utils.DayStatus
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansregular
import messon.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun DayConfigCard(
    day: String,
    status: DayStatus,
    onStatusChange: (DayStatus) -> Unit
) {
    val isCompulsory = status == DayStatus.Compulsory

    Surface(
        modifier = Modifier.height(90.dp),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    day,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    color = CustomColors.BlackberryCream)
                Text(
                    if (isCompulsory) "Auto-Billing" else "Manual Opt-in",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                    color = if (isCompulsory) CustomColors.RichCerulean else CustomColors.HoneyBronze
                )
            }

            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(CustomColors.OffWhite)
                    .padding(4.dp)
            ) {
                StatusToggleButton(
                    label = "Compulsory",
                    isSelected = isCompulsory,
                    activeColor = CustomColors.RichCerulean.copy(0.9f),
                    onClick = { onStatusChange(DayStatus.Compulsory) },
                    modifier = Modifier
                )
                StatusToggleButton(
                    label = "Optional",
                    isSelected = !isCompulsory,
                    activeColor = CustomColors.HoneyBronze.copy(0.8f),
                    onClick = { onStatusChange(DayStatus.Optional) },
                    modifier = Modifier
                )
            }
        }
    }
}

