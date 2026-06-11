package com.messon.mess.app.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun CollegeCard(
    collegeName: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .heightIn(min = 85.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface) // Fixed compilation error by mapping to semantic MaterialTheme surface
            .border(
                width = 1.dp, // Premium thin border profiles
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), // Adapts dynamically, avoiding harsh white lines in Dark Mode
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val (iconBox, nameText, arrowBtn, backgroundDecor) = createRefs()

            // Large abstract background brand decorator
            Icon(
                imageVector = Icons.Default.Business,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.03f), // Dynamic ultra-subtle tint for dark-mode harmony
                modifier = Modifier
                    .size(100.dp)
                    .constrainAs(backgroundDecor) {
                        top.linkTo(parent.top, margin = (-24).dp)
                        end.linkTo(parent.end, margin = (-16).dp)
                    }
            )

            // College icon container badge
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)) // Replaced hardcoded Color.White with premium transparent primary
                    .constrainAs(iconBox) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Business,
                    contentDescription = "College Icon",
                    tint = MaterialTheme.colorScheme.primary, // Dynamic primary highlight tint
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = collegeName,
                color = MaterialTheme.colorScheme.onSurface, // Dynamic text color
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                modifier = Modifier.constrainAs(nameText) {
                    start.linkTo(iconBox.end, margin = 16.dp)
                    end.linkTo(arrowBtn.start, margin = 8.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }
            )

            // Dynamic Right Chevron Action Box
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)) // Beautiful soft floating pocket background
                    .constrainAs(arrowBtn) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Navigate",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant, // Clean dynamic color adaptation
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}