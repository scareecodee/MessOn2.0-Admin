package com.messon.mess.app.presentation.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import messon.composeapp.generated.resources.publicsansregular
import org.jetbrains.compose.resources.Font

@Composable
fun EachNotificationItem(
    subject: String,
    description: String,
    iconColor: Color,
    modifier: Modifier,
    createdTime: String,
    painter: Painter,
    contentDescription: String
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface // Adaptive card background
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
            .animateContentSize()
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f), // Adaptive border contour
                shape = RoundedCornerShape(15.dp)
            )
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                isExpanded = !isExpanded
            }
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize().padding(5.dp)) {
            val (boxedIconRef, subjectRef, descriptionRef, clockIconRef, createdTimeRef, expandIconRef) = createRefs()

            BoxedIcon(
                modifier = Modifier
                    .constrainAs(boxedIconRef) {
                        start.linkTo(parent.start, margin = 10.dp)
                        top.linkTo(parent.top, margin = 15.dp)
                    }
                    .padding(5.dp),
                radius = 10,
                iconColor = iconColor,
                boxColor = iconColor.copy(alpha = 0.25f), // Slightly reduced alpha for better readability on light surfaces
                iconsSize = 25.dp,
                painter = painter,
                contentDescription = contentDescription
            )

            Text(
                overflow = TextOverflow.Ellipsis,
                maxLines = if (isExpanded) 100 else 1,
                text = subject,
                color = MaterialTheme.colorScheme.onSurface, // High contrast adaptive text color
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                modifier = Modifier.constrainAs(subjectRef) {
                    top.linkTo(boxedIconRef.top, margin = 5.dp)
                    end.linkTo(parent.end, margin = 15.dp)
                    start.linkTo(boxedIconRef.end, margin = 10.dp)
                    width = Dimension.fillToConstraints
                }
            )

            IconButton(
                enabled = false,
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier
                    .constrainAs(expandIconRef) {
                        end.linkTo(parent.end, margin = 5.dp)
                        start.linkTo(subjectRef.end)
                        top.linkTo(parent.top)
                    }
                    .padding(horizontal = 5.dp)
            ) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "expand btn",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant, // High contrast arrow indicator
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                overflow = TextOverflow.Ellipsis,
                maxLines = if (isExpanded) 100 else 3,
                text = description,
                color = MaterialTheme.colorScheme.onSurfaceVariant, // Muted but highly readable text color
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                modifier = Modifier.constrainAs(descriptionRef) {
                    top.linkTo(subjectRef.bottom, margin = 9.dp)
                    end.linkTo(parent.end, margin = 5.dp)
                    start.linkTo(subjectRef.start)
                    width = Dimension.fillToConstraints
                }
            )

            Icon(
                imageVector = Icons.Outlined.Schedule,
                contentDescription = "time",
                modifier = Modifier
                    .size(18.dp)
                    .constrainAs(clockIconRef) {
                        start.linkTo(subjectRef.start)
                        top.linkTo(descriptionRef.bottom, margin = 16.dp)
                        bottom.linkTo(parent.bottom, margin = 10.dp)
                    },
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f) // Dynamic clock tint
            )

            Text(
                text = createdTime,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f), // Dynamic timestamp tint
                fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                modifier = Modifier
                    .constrainAs(createdTimeRef) {
                        top.linkTo(clockIconRef.top)
                        bottom.linkTo(clockIconRef.bottom)
                        start.linkTo(clockIconRef.end, margin = 5.dp)
                    }
                    .padding(horizontal = 5.dp)
            )
        }
    }
}