package com.messon.mess.app.presentation.ui.component
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Eco
import androidx.compose.material.icons.rounded.SetMeal
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsansregular
import messon.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun MealScanCard(
    mealName: String, icon: ImageVector, totalScans: String, vegScans: String, nonVegScans: String,
    bgHex: Color, accentHex: Color
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgHex, RoundedCornerShape(24.dp))
            .border(1.dp, accentHex.copy(alpha = 0.2f), RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) {
        val (iconBox, title, total, totalLbl, pillsRow) = createRefs()

        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(14.dp))
                .border(1.dp, Color.White, RoundedCornerShape(14.dp))
                .constrainAs(iconBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = accentHex, modifier = Modifier.size(24.dp))
        }

        Text(
            text = mealName,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(iconBox.top)
                bottom.linkTo(iconBox.bottom)
                start.linkTo(iconBox.end, margin = 12.dp)
            }
        )

        Text(
            text = totalScans,
            fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
            fontSize = 22.sp,
            color = accentHex,
            modifier = Modifier.constrainAs(total) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = "Total Scans",
            fontFamily = FontFamily(Font(Res.font.publicsansregular)),
            fontSize = 10.sp,
            color = accentHex.copy(alpha = 0.7f),
            letterSpacing = 1.sp,
            modifier = Modifier.constrainAs(totalLbl) {
                top.linkTo(total.bottom, margin = 4.dp)
                end.linkTo(parent.end)
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(pillsRow) {
                    top.linkTo(iconBox.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Rounded.Eco, contentDescription = "Veg", tint = Color.Green, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = vegScans,
                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Veg",
                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                    fontSize = 11.sp,
                    color = Color.Green
                )
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Rounded.SetMeal, contentDescription = "Non-Veg", tint = Color.Red, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = nonVegScans,
                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Non-Veg",
                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                    fontSize = 11.sp,
                    color = Color.Red
                )
            }
        }
    }
}
