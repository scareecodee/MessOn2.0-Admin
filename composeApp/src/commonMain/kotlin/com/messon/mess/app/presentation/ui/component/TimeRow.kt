package com.messon.mess.app.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
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
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsansregular
import org.jetbrains.compose.resources.Font

@Composable
fun TimeRow(
    label: String,
    time: String,
    icon: ImageVector,
    iconColor: Color,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    Color.Black.copy(alpha = 0.03f),
                    RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = iconColor)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {

            Text(
                label,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                color = Color.LightGray
            )

            Text(
                time,
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                color = Color(0xFF333333)
            )
        }

        Icon(
            Icons.Default.ChevronRight,
            null,
            tint = Color.LightGray,
            modifier = Modifier.size(30.dp)
        )
    }
}
