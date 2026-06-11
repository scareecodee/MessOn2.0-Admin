package com.messon.mess.app.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
        // Replaced static white container color with dynamic adaptive surface
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
        ),
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
                    text = day,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                    // Replaced BlackberryCream with dynamic onSurface text
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = if (isCompulsory) "Auto-Billing" else "Manual Opt-in",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                    // Swapped custom colors with primary and secondary theme accents
                    color = if (isCompulsory) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    }
                )
            }

            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    // Replaced OffWhite with responsive glassmorphic overlay base
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                    .padding(4.dp)
            ) {
                StatusToggleButton(
                    label = "Compulsory",
                    isSelected = isCompulsory,
                    activeColor = MaterialTheme.colorScheme.primary,
                    onClick = { onStatusChange(DayStatus.Compulsory) },
                    modifier = Modifier
                )
                StatusToggleButton(
                    label = "Optional",
                    isSelected = !isCompulsory,
                    activeColor = MaterialTheme.colorScheme.secondary,
                    onClick = { onStatusChange(DayStatus.Optional) },
                    modifier = Modifier
                )
            }
        }
    }
}

