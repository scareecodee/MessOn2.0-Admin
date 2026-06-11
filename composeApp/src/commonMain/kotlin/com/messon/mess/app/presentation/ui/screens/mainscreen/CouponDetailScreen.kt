package com.messon.mess.app.presentation.ui.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.mess.app.presentation.viewmodel.CouponViewModel
import com.messon.mess.app.theme.MessonTheme
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansbold
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsansregular
import messon.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font

@Composable
fun CouponDetailScreen(
    onScanNext: () -> Unit,
    couponViewModel: CouponViewModel,
) {
    val scanResult = couponViewModel.couponDetail.collectAsState().value
    val isValid = scanResult?.isValid ?: false
    val colors = MaterialTheme.colorScheme

    ConstraintLayout(
        modifier = Modifier.fillMaxSize().background(colors.background),
    ) {
        val (statusBarBox, headingRef, listRef) = createRefs()

        Box(
            Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(MessonTheme.colors.divider)
                .constrainAs(statusBarBox) { top.linkTo(parent.top) }
        )

        Text(
            text = "Coupon Detail",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.publicsansbold)),
            color = colors.onSurface,
            modifier = Modifier.constrainAs(headingRef) {
                start.linkTo(parent.start, 15.dp)
                top.linkTo(statusBarBox.bottom, margin = 5.dp)
            }
        )

        LazyColumn(
            modifier = Modifier.constrainAs(listRef) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(headingRef.bottom, margin = 5.dp)
                bottom.linkTo(parent.bottom, margin = 5.dp)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(start = 18.dp, end = 18.dp, bottom = 20.dp)
        ) {
            item { Spacer(modifier = Modifier.height(32.dp)) }

            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(if (isValid) colors.primary else colors.error),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isValid) Icons.Default.Check else Icons.Default.Close,
                            contentDescription = "Status",
                            tint = colors.onPrimary,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = if (isValid) "Scan Successful" else "Scan Failed",
                        fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                        fontSize = 16.sp,
                        color = colors.onSurface
                    )

                    Text(
                        text = scanResult?.errors?.joinToString("\n") { it.message } ?: "",
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                        fontSize = 12.sp,
                        color = colors.error,
                        textAlign = TextAlign.Center
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(40.dp),
                    colors = CardDefaults.cardColors(containerColor = colors.surface),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MessonTheme.colors.infoContainer)
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier.size(80.dp).clip(RoundedCornerShape(24.dp)).background(colors.surface),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    tint = colors.primary,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = scanResult?.studentName ?: "",
                                fontFamily = FontFamily(Font(Res.font.publicsansbold)),
                                fontSize = 16.sp,
                                color = colors.onSurface,
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = "${scanResult?.rollNo} • ${scanResult?.branch}",
                                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                                fontSize = 12.sp,
                                color = colors.primary,
                                textAlign = TextAlign.Center
                            )
                        }

                        ConstraintLayout(modifier = Modifier.fillMaxWidth().padding(32.dp)) {
                            val (slotLabel, slotValue, typeLabel, typeValue, footer) = createRefs()

                            Text(
                                text = "Meal Slot",
                                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                                fontSize = 16.sp,
                                color = colors.onSurfaceVariant,
                                letterSpacing = 1.sp,
                                modifier = Modifier.constrainAs(slotLabel) { top.linkTo(parent.top); start.linkTo(parent.start) }
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.constrainAs(slotValue) { top.linkTo(slotLabel.bottom, 4.dp); start.linkTo(parent.start) }
                            ) {
                                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(colors.secondary))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = scanResult?.mealSlot ?: "Not found",
                                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                                    fontSize = 12.sp,
                                    color = colors.onSurface
                                )
                            }

                            Text(
                                text = "Meal Type",
                                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                                fontSize = 16.sp,
                                color = colors.onSurfaceVariant,
                                letterSpacing = 1.sp,
                                modifier = Modifier.constrainAs(typeLabel) { top.linkTo(parent.top); end.linkTo(parent.end) }
                            )

                            Text(
                                text = scanResult?.mealType ?: "Not found",
                                fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                                fontSize = 14.sp,
                                color = colors.onSurface,
                                modifier = Modifier.constrainAs(typeValue) { top.linkTo(typeLabel.bottom, 4.dp); start.linkTo(typeLabel.start) }
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(colors.background)
                                    .padding(16.dp)
                                    .constrainAs(footer) { top.linkTo(slotValue.bottom, 24.dp); start.linkTo(parent.start); end.linkTo(parent.end) },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.History,
                                    contentDescription = null,
                                    tint = colors.onSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text("Scanned At", fontFamily = FontFamily(Font(Res.font.publicsanssemibold)), fontSize = 12.sp, color = colors.onSurfaceVariant)
                                    Text(scanResult?.scannedAt ?: "Not found", fontFamily = FontFamily(Font(Res.font.publicsansmedium)), fontSize = 14.sp, color = colors.onSurface)
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }

            item {
                Button(
                    onClick = onScanNext,
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colors.onSurface)
                ) {
                    Text(
                        text = "Scan Next Coupon",
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                        fontSize = 14.sp,
                        letterSpacing = 1.sp,
                        color = colors.surface
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                Text(
                    text = "Already used coupons will be flagged by the system immediately.",
                    fontFamily = FontFamily(Font(Res.font.publicsansregular)),
                    fontSize = 12.sp,
                    color = colors.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }
        }
    }
}