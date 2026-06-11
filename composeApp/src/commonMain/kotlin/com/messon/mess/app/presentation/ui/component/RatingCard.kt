package com.messon.mess.app.presentation.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.ModeNight
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.mess.app.utils.MealSlot
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsansregular
import messon.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun RatingCard(
    mealSlot: String,
    avgRating: Float?,
    vegRating: Float?,
    nonVegRating: Float?,
    maxRating: Int,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            val (iconBox, totalLabel, totalValue, titleText, dataSection) = createRefs()

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = accentColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .constrainAs(iconBox) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (mealSlot.lowercase()) {
                        MealSlot.BREAKFAST.value -> Icons.Outlined.Coffee
                        MealSlot.LUNCH.value -> Icons.Outlined.WbSunny
                        MealSlot.DINNER.value -> Icons.Outlined.ModeNight
                        else -> Icons.Default.FoodBank
                    },
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = "Total Scans",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.constrainAs(totalLabel) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
            )

            Text(
                text = if (avgRating != null) "$avgRating/${maxRating}" else "__",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.constrainAs(totalValue) {
                    top.linkTo(totalLabel.bottom, margin = 2.dp)
                    end.linkTo(parent.end)
                }
            )

            Text(
                text = mealSlot.replaceFirstChar { it.uppercaseChar() },
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.constrainAs(titleText) {
                    top.linkTo(iconBox.bottom, margin = 24.dp)
                    start.linkTo(parent.start)
                }
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.constrainAs(dataSection) {
                    top.linkTo(titleText.bottom, margin = 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            ) {
                StatProgressBar(
                    label = "Veg",
                    barColor = MaterialTheme.colorScheme.primary,
                    rating = vegRating,
                    maxRating = maxRating
                )

                StatProgressBar(
                    label = "Non-Veg",
                    barColor = MaterialTheme.colorScheme.secondary, // Dynamic theme secondary (HoneyBronze in Light, DarkHoneyBronze in Dark)
                    rating = nonVegRating,
                    maxRating = maxRating
                )
            }
        }
    }
}

@Composable
private fun StatProgressBar(
    label: String,
    rating: Float?,
    maxRating: Int,
    barColor: Color
) {
    val progress = if (rating == null || maxRating == 0) {
        0f
    } else {
        (rating / maxRating).coerceIn(0f, 1f)
    }
    val safeProgress = if (progress.isNaN()) 0f else progress

    val animatedProgress by animateFloatAsState(
        targetValue = safeProgress,
        animationSpec = tween(durationMillis = 1000),
        label = "ProgressAnimation"
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(barColor, RoundedCornerShape(50))
                )
                Text(
                    text = label,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Text(
                text = rating?.let { "$it / $maxRating" } ?: "No rating available",
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                color = if (rating == null) MaterialTheme.colorScheme.onSurfaceVariant else barColor
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedProgress)
                    .background(barColor)
            )
        }
    }
}