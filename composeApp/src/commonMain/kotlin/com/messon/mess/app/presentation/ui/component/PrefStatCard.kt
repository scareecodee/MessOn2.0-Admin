package com.messon.mess.app.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.mess.app.domain.dataModel.EachDayPrefStat
import com.messon.mess.app.domain.dataModel.EachSlotPrefStat
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsansregular
import messon.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun PrefStatCard(day: EachDayPrefStat) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                shape = RoundedCornerShape(30.dp)
            )
    ) {
        val (header, mealList, footer) = createRefs()

        Column(
            modifier = Modifier
                .padding(25.dp)
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = day.day.replaceFirstChar { it.uppercaseChar() },
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                        color = MaterialTheme.colorScheme.onSurface // Dynamic primary header text
                    )
                    Text(
                        text = day.date,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                        color = MaterialTheme.colorScheme.onSurfaceVariant // Muted secondary subtext
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp)
                .constrainAs(mealList) {
                    top.linkTo(header.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            MealEntry(
                label = "Breakfast",
                icon = Icons.Default.Coffee,
                data = day.breakfastStat,
                color = MaterialTheme.colorScheme.primary
            )
            MealEntry(
                label = "Lunch",
                icon = Icons.Default.WbSunny,
                data = day.lunchStat,
                color = MaterialTheme.colorScheme.secondary
            )
            MealEntry(
                label = "Dinner",
                icon = Icons.Default.NightsStay,
                data = day.dinnerStat,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
fun MealEntry(label: String, icon: ImageVector, data: EachSlotPrefStat, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(color.copy(alpha = 0.08f))
            .border(
                width = 1.dp,
                color = color.copy(alpha = 0.15f),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(vertical = 12.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface) // Adaptive surface inner capsule
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = label,
            modifier = Modifier.weight(1f),
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)), // Swapped to semibold for modern readability
            color = MaterialTheme.colorScheme.onSurface
        )

        // Dynamic badges mapping to cohesive accent colors
        MealCountBadge(
            tag = "V",
            count = data.vegCount ?: 0,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        MealCountBadge(
            tag = "NV",
            count = data.nonVegCount ?: 0,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun MealCountBadge(tag: String, count: Int, color: Color) {
    Column(
        modifier = Modifier
            .width(56.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surface) // Adaptive backing
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = tag,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f), // Dynamic unselected subtext
            fontSize = 10.sp,
            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
        )
        Text(
            text = count.toString(),
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
            color = color // Dynamic category badge number (Primary for Veg, Secondary for Non-Veg)
        )
    }
}