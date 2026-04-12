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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.messon.mess.app.presentation.ui.component.ScanStatCard
import com.messon.mess.app.presentation.ui.state.ScanStatState
import com.messon.mess.app.presentation.viewmodel.ScanStatVM
import com.messon.mess.app.theme.CustomColors
import com.messon.mess.app.theme.CustomColors.BlackberryCream
import com.messon.mess.app.theme.CustomColors.HoneyBronze
import com.messon.mess.app.theme.CustomColors.LightBlue
import com.messon.mess.app.theme.CustomColors.NonVegRed
import com.messon.mess.app.theme.CustomColors.OffWhite
import com.messon.mess.app.theme.CustomColors.RichCerulean
import com.messon.mess.app.utils.MealSlot
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.publicsanssemibold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font

@Composable
fun HomeScreen(
    modifier: Modifier,
    onPrefAnalyticsClick:()-> Unit,
    onBellIconClick:()-> Unit,
    onComplaintsClick:()-> Unit,
    onRatingsClick:()-> Unit,
    onNotificationClick:()-> Unit,
    onScanAnalyticsClick:()-> Unit,
    onMessMenuClick:()-> Unit,
    onDayPolicyClick:()-> Unit,
    onMessTimingClick:()-> Unit,
    onWindowTimingClick:()-> Unit,
    scanStatVM: ScanStatVM
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val fetchState by scanStatVM.uiState.collectAsState()
    val isRefreshing=fetchState is ScanStatState.Loading
    val stats by scanStatVM.filteredStats.collectAsState()
    val breakfast = stats.find { it.mealSlot == MealSlot.BREAKFAST.value }
    val lunch = stats.find { it.mealSlot == MealSlot.LUNCH.value }
    val dinner = stats.find { it.mealSlot == MealSlot.DINNER.value }

    LaunchedEffect(Unit){
        scanStatVM.fetchTodayScanStat()
    }
    LaunchedEffect(fetchState){
        when(fetchState){
            is ScanStatState.Error -> {
                showToast((fetchState as ScanStatState.Error).message)
                scanStatVM.clearFetchState()
            }
            else -> {}
        }
    }
    ConstraintLayout(
        modifier =modifier
            .fillMaxSize()
            .background(CustomColors.OffWhite)
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

            PullToRefreshBox(
                state = pullToRefreshState,
                indicator = {
                    PullToRefreshDefaults.Indicator(
                        containerColor = CustomColors.RichCerulean,
                        color = Color.White,
                        isRefreshing = isRefreshing,
                        state = pullToRefreshState
                    )
                },
                isRefreshing =isRefreshing,
                onRefresh = {
                    scanStatVM.fetchTodayScanStat()
                },
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(scrollContent) {
                        top.linkTo(header.bottom, margin =(-30).dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    }                        .zIndex(2f)
                ,
                contentAlignment = Alignment.TopCenter
            ){

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 40.dp)
                ) {
                    item {
                        ScanStatCard(
                            mealSlot = MealSlot.BREAKFAST.value,
                            totalCount = breakfast?.totalScanned ?: 0,
                            vegCount = breakfast?.scannedVeg ?: 0,
                            nonVegCount = breakfast?.scannedNonVeg ?: 0,
                            accentColor = RichCerulean,
                            maxVegCapacity = breakfast?.totalVeg ?:0,
                            maxNonVegCapacity = breakfast?.totalNonVeg ?:0
                        )
                    }

                    item {
                        ScanStatCard(
                            mealSlot = MealSlot.LUNCH.value,
                            totalCount = lunch?.totalScanned ?: 0,
                            vegCount = lunch?.scannedVeg ?: 0,
                            nonVegCount = lunch?.scannedNonVeg ?: 0,
                            accentColor = HoneyBronze,
                            maxVegCapacity = lunch?.totalVeg ?: 0,
                            maxNonVegCapacity = lunch?.totalNonVeg ?: 0
                        )
                    }

                    item {
                        ScanStatCard(
                            mealSlot = MealSlot.DINNER.value,
                            totalCount = dinner?.totalScanned ?: 0,
                            vegCount = dinner?.scannedVeg ?: 0,
                            nonVegCount = dinner?.scannedNonVeg ?: 0,
                            accentColor = BlackberryCream,
                            maxVegCapacity = dinner?.totalVeg ?: 0,
                            maxNonVegCapacity = dinner?.totalNonVeg ?: 0
                        )
                    }

                    item{
                        FeatureModule(title = "Insights & Feedback") {
                            FeatureGrid(
                                items = listOf(
                                    FeatureData(
                                        "Pref Analytics", Icons.Rounded.PieChart, RichCerulean,
                                        onClick =onPrefAnalyticsClick
                                    ),
                                    FeatureData(
                                        "Scan Analytics", Icons.Rounded.TrendingUp, RichCerulean,
                                        onClick ={
                                            onScanAnalyticsClick()
                                        }
                                    ),
                                    FeatureData(
                                        "Meal Ratings", Icons.Rounded.Star, HoneyBronze,
                                        onClick = {
                                            onRatingsClick()
                                        }
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




