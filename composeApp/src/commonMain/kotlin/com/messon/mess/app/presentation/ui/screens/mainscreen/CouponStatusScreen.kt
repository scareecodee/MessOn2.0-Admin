package com.messon.mess.app.presentation.ui.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.mess.app.theme.MessonTheme
import com.messon.mess.app.utils.SchedulerStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun CouponStatusScreen(
    onBackBtnClick: () -> Unit
) {
    var status by remember { mutableStateOf(SchedulerStatus.FAILED) }
    var isGenerating by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val colors = MaterialTheme.colorScheme
    val messonColors = MessonTheme.colors

    ConstraintLayout(
        modifier = Modifier.fillMaxSize().background(colors.background)
    ) {
        val (lazyColumn, statusBarBox, backBtnRef, headingRef) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(colors.outline)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                }
        )

        IconButton(
            shape = CircleShape,
            onClick = onBackBtnClick,
            modifier = Modifier.constrainAs(backBtnRef) {
                start.linkTo(parent.start, margin = 15.dp)
                top.linkTo(statusBarBox.bottom, margin = 10.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "back btn",
                tint = colors.onSurface,
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            maxLines = 1,
            text = "Coupon Status",
            fontSize = 20.sp,
            color = colors.onSurface,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            modifier = Modifier.constrainAs(headingRef) {
                start.linkTo(backBtnRef.end, margin = 5.dp)
                top.linkTo(backBtnRef.top)
                bottom.linkTo(backBtnRef.bottom)
            }
        )

        LazyColumn(
            modifier = Modifier.constrainAs(lazyColumn) {
                top.linkTo(headingRef.bottom, margin = 10.dp)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            contentPadding = PaddingValues(bottom = 16.dp, start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(40.dp))

                Card(
                    colors = CardDefaults.cardColors(containerColor = colors.surface),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                        val (accentBar, title, badge, alertBox, footer) = createRefs()

                        Box(
                            modifier = Modifier
                                .height(8.dp)
                                .fillMaxWidth()
                                .background(if (status == SchedulerStatus.SUCCESS) colors.primary else messonColors.warningContainer)
                                .constrainAs(accentBar) { top.linkTo(parent.top) }
                        )

                        Text(
                            text = "Scheduler Status",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                            color = colors.onSurface,
                            modifier = Modifier.constrainAs(title) {
                                top.linkTo(accentBar.bottom, margin = 24.dp)
                                start.linkTo(parent.start, margin = 20.dp)
                            }
                        )

                        StatusBadge(
                            status = status,
                            modifier = Modifier.constrainAs(badge) {
                                top.linkTo(title.top)
                                bottom.linkTo(title.bottom)
                                start.linkTo(title.end)
                                end.linkTo(parent.end)
                            }
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (status == SchedulerStatus.SUCCESS) messonColors.infoContainer else messonColors.warningContainer)
                                .padding(20.dp)
                                .constrainAs(alertBox) {
                                    top.linkTo(title.bottom, margin = 24.dp)
                                    start.linkTo(parent.start, margin = 20.dp)
                                    end.linkTo(parent.end, margin = 20.dp)
                                    width = Dimension.fillToConstraints
                                }
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (status == SchedulerStatus.SUCCESS) Icons.Default.CheckCircle else Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = if (status == SchedulerStatus.SUCCESS) messonColors.success else MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = if (status == SchedulerStatus.SUCCESS)
                                        "Automated generation successful. No action needed."
                                    else "Automated generation failed. Please trigger manually.",
                                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                                    color = colors.onSurface,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    lineHeight = 20.sp
                                )
                            }
                        }

                        Column(
                            modifier = Modifier
                                .background(messonColors.infoContainer.copy(0.7f))
                                .padding(horizontal = 20.dp, vertical = 32.dp)
                                .constrainAs(footer) {
                                    top.linkTo(alertBox.bottom, margin = 32.dp)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom)
                                    width = Dimension.fillToConstraints
                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = {

                                },
                                colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                                enabled = !isGenerating,
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier.fillMaxWidth().height(56.dp),
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                            ) {
                                    Text("Generate All Coupons Manually",
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                                        fontSize = 12.sp,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(Icons.Default.KeyboardArrowRight,
                                        tint=Color.White,
                                        contentDescription = null)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: SchedulerStatus, modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colorScheme
    val messonColors = MessonTheme.colors
    val isSuccess = status == SchedulerStatus.SUCCESS
    val bgColor = if (isSuccess) colors.primary.copy(alpha = 0.15f) else messonColors.warningContainer.copy(0.15f)
    val textColor = if (isSuccess) colors.primary else colors.secondary

    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(bgColor)
            .border(1.dp, textColor.copy(alpha = 0.2f), CircleShape)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(textColor)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = if (isSuccess) "Successfull" else "Action Required",
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            fontSize = 10.sp,
            color = textColor,
        )
    }
}