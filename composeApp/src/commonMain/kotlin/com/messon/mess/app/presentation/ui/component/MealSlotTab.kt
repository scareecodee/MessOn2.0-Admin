package com.messon.mess.app.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.mess.app.utils.MealSlot
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansmedium
import org.jetbrains.compose.resources.Font

@Composable
fun MealSlotTabs(
    activeSlot: String,
    iconColor: Color,
    onSlotSelected: (String) -> Unit
) {
    val slots = listOf(
        Triple("B", MealSlot.BREAKFAST.value.replaceFirstChar { it.uppercase() }, Icons.Default.Coffee),
        Triple("L", MealSlot.LUNCH.value.replaceFirstChar { it.uppercase() }, Icons.Default.WbSunny),
        Triple("D", MealSlot.DINNER.value.replaceFirstChar { it.uppercase() }, Icons.Default.NightsStay)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f))
            .padding(6.dp)
    ) {
        slots.forEach { (id, label, icon) ->
            val isSelected = activeSlot == id

            val tabBackground = if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent
            val textThemeColor = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            val iconThemeColor = if (isSelected) iconColor else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(tabBackground)
                    .clickable { onSlotSelected(id) }
                    .padding(vertical = 12.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = iconThemeColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = label,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                    color = textThemeColor
                )
            }
        }
    }
}