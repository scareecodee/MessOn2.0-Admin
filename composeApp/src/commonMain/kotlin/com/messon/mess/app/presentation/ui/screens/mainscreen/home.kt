package com.messon.mess.app.presentation.ui.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.mess.app.presentation.ui.component.FeatureData
import com.messon.mess.app.presentation.ui.component.FeatureGrid
import com.messon.mess.app.presentation.ui.component.FeatureModule
import com.messon.mess.app.presentation.ui.component.MealScanCard
import com.messon.mess.app.theme.CustomColors
import com.messon.mess.app.theme.CustomColors.BlackberryCream
import com.messon.mess.app.theme.CustomColors.Cream
import com.messon.mess.app.theme.CustomColors.HoneyBronze
import com.messon.mess.app.theme.CustomColors.LightBlue
import com.messon.mess.app.theme.CustomColors.LightLavender
import com.messon.mess.app.theme.CustomColors.NonVegRed
import com.messon.mess.app.theme.CustomColors.OffWhite
import com.messon.mess.app.theme.CustomColors.RichCerulean
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsanssemibold
import org.jetbrains.compose.resources.Font


@Composable
fun HomeScreen(
    modifier: Modifier,
    onBellIconClick:()-> Unit,
    onComplaintsClick:()-> Unit,
    onRatingsClick:()-> Unit,
    onNotificationClick:()-> Unit,
    onMessMenuClick:()-> Unit,
    onDayPolicyClick:()-> Unit,
    onMessTimingClick:()-> Unit,
    onWindowTimingClick:()-> Unit
) {
    ConstraintLayout(
        modifier =modifier
            .fillMaxSize()
            .background(Color(0xFFE8ECEF))
    ) {
        val (appContainer) = createRefs()

        ConstraintLayout(
            modifier = Modifier
                .widthIn(max = 420.dp)
                .fillMaxHeight()
                .background(OffWhite)
                .constrainAs(appContainer) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            val (header, scrollContent) = createRefs()

            DashboardHeader(
                modifier = Modifier
                    .constrainAs(header) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .zIndex(1f)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .constrainAs(scrollContent) {
                        top.linkTo(header.bottom, margin = (-32).dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    }
                    .zIndex(2f)
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 40.dp)
            ) {
                item{
                    MealScanCard(
                        mealName = "Breakfast", icon = Icons.Rounded.LocalCafe,
                        totalScans = "60", vegScans = "40", nonVegScans = "20",
                        bgHex = LightBlue, accentHex = RichCerulean
                    )
                }

                item{
                    MealScanCard(
                        mealName = "Lunch", icon = Icons.Rounded.WbSunny,
                        totalScans = "256", vegScans = "150", nonVegScans = "106",
                        bgHex = Cream, accentHex = HoneyBronze
                    )
                }

                item{
                    MealScanCard(
                        mealName = "Dinner", icon = Icons.Rounded.ModeNight,
                        totalScans = "0", vegScans = "0", nonVegScans = "0",
                        bgHex = LightLavender, accentHex = BlackberryCream
                    )
                }

                item{
                    FeatureModule(title = "Insights & Feedback") {
                        FeatureGrid(
                            items = listOf(
                                FeatureData(
                                    "Pref Analytics", Icons.Rounded.PieChart, RichCerulean,
                                    onClick ={}
                                ),
                                FeatureData(
                                    "Scan Analytics", Icons.Rounded.TrendingUp, RichCerulean,
                                    onClick ={}
                                ),
                                FeatureData(
                                    "Meal Ratings", Icons.Rounded.Star, HoneyBronze,
                                    onClick = {}
                                ),
                                FeatureData(
                                    "Complaints", Icons.Rounded.Warning, CustomColors.Coral,
                                    onClick = {}
                                )
                            )
                        )
                    }
                }


                item{
                    FeatureModule(title = "Operations & Policy") {
                        FeatureGrid(
                            items = listOf(
                                FeatureData(
                                    "Mess Menu", Icons.Rounded.MenuBook, CustomColors.Teal,
                                    onClick = onMessMenuClick
                                ),
                                FeatureData(
                                    "Day Policy", Icons.Rounded.Security, CustomColors.Teal,
                                    onClick = onDayPolicyClick
                                ),
                                FeatureData(
                                    "Generate Coupons",
                                    Icons.Rounded.ConfirmationNumber,
                                    BlackberryCream,
                                    onClick ={}
                                ),
                                FeatureData(
                                    "Notifications",
                                    Icons.Rounded.NotificationsActive,
                                    BlackberryCream,
                                    onClick = {}
                                )
                            )
                        )
                    }
                }


                item{
                    FeatureModule(title = "Timings & Alerts") {
                        FeatureGrid(
                            items = listOf(
                                FeatureData(
                                    "Mess Timing", Icons.Rounded.Schedule, Color(0xFF8B5CF6),
                                    onClick = onMessTimingClick
                                ),
                                FeatureData(
                                    "Window Timing", Icons.Rounded.Timer, Color(0xFF8B5CF6),
                                    onClick = onWindowTimingClick
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardHeader(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                spotColor = Color.Black.copy(alpha = 0.05f)
            )
            .background(Color.White, RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .padding(start = 24.dp, end = 24.dp, top = 48.dp, bottom = 64.dp) // Extra bottom padding for overlap
    ) {
        val (locationPill, bellIcon, greetingText, subText) = createRefs()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.constrainAs(locationPill) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(LightBlue, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Rounded.LocationOn, contentDescription = null, tint = RichCerulean, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "IIIT Bhubaneswar",
                        fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                        fontSize = 14.sp,
                        color = BlackberryCream
                    )
                    Icon(
                        Icons.Rounded.KeyboardArrowDown,
                        contentDescription = null,
                        tint = BlackberryCream.copy(alpha = 0.6f),
                        modifier = Modifier.size(16.dp).padding(start = 4.dp)
                    )
                }
                Text(
                    text = "Swain Caterers • Admin",
                    fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
                    fontSize = 12.sp,
                    color = BlackberryCream.copy(alpha = 0.6f)
                )
            }
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(OffWhite, CircleShape)
                .border(1.dp, Color.LightGray.copy(alpha = 0.3f), CircleShape)
                .constrainAs(bellIcon) {
                    top.linkTo(locationPill.top)
                    bottom.linkTo(locationPill.bottom)
                    end.linkTo(parent.end)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Rounded.Notifications, contentDescription = null, tint = BlackberryCream, modifier = Modifier.size(20.dp))
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(NonVegRed, CircleShape)
                    .border(1.dp, Color.White, CircleShape)
                    .align(Alignment.TopEnd)
                    .offset(x = (-8).dp, y = 8.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.constrainAs(greetingText) {
                top.linkTo(locationPill.bottom, margin = 24.dp)
                start.linkTo(parent.start)
            }
        ) {
            Text(
                text = "Hey there!",
                fontFamily = FontFamily(Font(Res.font.publicsanssemibold)),
                fontSize = 22.sp,
                color = Color.Black
            )
            Text(
                text = " 👋",
                fontSize = 24.sp
            )
        }

        Text(
            text = "Here's your mess overview for today",
            fontFamily = FontFamily(Font(Res.font.publicsansmedium)),
            fontSize = 13.sp,
            color = BlackberryCream.copy(alpha = 0.5f),
            modifier = Modifier.constrainAs(subText) {
                top.linkTo(greetingText.bottom, margin = 4.dp)
                start.linkTo(parent.start)
            }
        )
    }
}




