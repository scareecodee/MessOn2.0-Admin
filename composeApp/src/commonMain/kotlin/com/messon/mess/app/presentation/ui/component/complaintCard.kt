package com.messon.mess.app.presentation.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsansregular
import org.jetbrains.compose.resources.Font

@Composable
fun ComplaintCard(
    modifier: Modifier,
    subject: String,
    description: String,
    createdAt: String,
    sentBy: String,

    ) {
    var isExpanded by remember { mutableStateOf(false) }
    val colors = MaterialTheme.colorScheme

    Card(
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = modifier.animateContentSize()
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .border(
                width = 0.5.dp,
                color = colors.outline.copy(alpha = 0.5f),
                shape = RoundedCornerShape(15.dp)
            )
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                isExpanded = !isExpanded
            }

    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize().padding(5.dp)) {
            val (dividerRef, boxedIconRef, subjectRef, descriptionRef, createdTimeRef, expandIconRef, sentByRef, circledNameRef) = createRefs()
            Icon(
                imageVector = Icons.Outlined.ErrorOutline,
                contentDescription = "complaint",
                modifier = Modifier
                    .padding(5.dp)
                    .size(30.dp)
                    .constrainAs(boxedIconRef) {
                        start.linkTo(parent.start, margin = 10.dp)
                        top.linkTo(parent.top, margin = 15.dp)
                    },
                tint = colors.primary
            )

            Text(
                overflow = TextOverflow.Ellipsis,
                maxLines = if (isExpanded) 100 else 1,
                text = subject,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                color = colors.onSurface,
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
                modifier = Modifier.constrainAs(expandIconRef) {
                    end.linkTo(parent.end, margin = 5.dp)
                    start.linkTo(subjectRef.end)
                    top.linkTo(parent.top)
                }
                    .padding(horizontal = 5.dp)
            ) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "expand btn",
                    tint = colors.outline,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                overflow = TextOverflow.Ellipsis,
                maxLines = if (isExpanded) 100 else 3,
                text = description,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                color = colors.onSurfaceVariant,
                modifier = Modifier.constrainAs(descriptionRef) {
                    top.linkTo(subjectRef.bottom, margin = 9.dp)
                    end.linkTo(parent.end, margin = 5.dp)
                    start.linkTo(subjectRef.start)
                    width = Dimension.fillToConstraints
                }
            )

            HorizontalDivider(
                thickness = 0.3.dp,
                color = colors.outline.copy(alpha = 0.5f),
                modifier = Modifier.constrainAs(dividerRef) {
                    top.linkTo(descriptionRef.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                    .fillMaxWidth()

            )

            CircledName(
                modifier = Modifier.constrainAs(circledNameRef) {
                    start.linkTo(parent.start, margin = 10.dp)
                    top.linkTo(dividerRef.top, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                },
                letter = sentBy.getOrNull(0)?.toString() ?: "?",
                color = colors.onSurface
            )
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = sentBy,
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                color = colors.onSurface,
                modifier = Modifier.constrainAs(sentByRef) {
                    start.linkTo(circledNameRef.end, margin = 8.dp)
                    top.linkTo(circledNameRef.top)
                    bottom.linkTo(circledNameRef.bottom)
                }
            )
            Text(
                fontSize = 9.sp,
                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                text = createdAt,
                color = colors.outline,
                modifier = Modifier.constrainAs(createdTimeRef) {
                    end.linkTo(parent.end, margin = 10.dp)
                    top.linkTo(circledNameRef.top)
                    bottom.linkTo(circledNameRef.bottom)
                }
            )
        }
    }
}

@Composable
fun CircledName(
    modifier: Modifier,
    letter: String,
    color: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(22.dp)
            .background(
                color = color.copy(alpha = 0.15f),
                shape = CircleShape
            )
    ) {
        Text(
            color = color,
            text = letter,
            fontSize = 9.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
        )
    }
}