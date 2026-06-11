package com.messon.mess.app.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun MealCard(
    mealSlot: String,
    dishes: String,
    tint: Color, // Pass e.g., MaterialTheme.customColors.infoContainer
    accent: Color, // Pass e.g., your primary color or a specific icon color
    icon: ImageVector
) {
    // Access your custom theme extensions here
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(38.dp),
            // Use 'surface' for the card background so it adapts to dark mode
            colors = CardDefaults.cardColors(containerColor = colors.surface),
            // Use 'outline' color with low alpha for a subtle professional border
            border = BorderStroke(1.dp, colors.outline.copy(alpha = 0.2f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 64.dp, height = 80.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(tint),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, null, tint = accent, modifier = Modifier.size(28.dp))
                }

                Spacer(modifier = Modifier.width(20.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        mealSlot,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                        // Using 'onSurface' with lower alpha provides great contrast without looking "gray"
                        color = colors.onSurface.copy(alpha = 0.6f),
                        letterSpacing = 1.sp
                    )
                    Text(
                        dishes,
                        fontSize = 14.sp, // Slightly increased for better readability
                        fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                        color = colors.onSurface,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}