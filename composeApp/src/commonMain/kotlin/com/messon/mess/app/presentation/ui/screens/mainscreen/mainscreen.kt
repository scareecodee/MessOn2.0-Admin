package com.messon.mess.app.presentation.ui.screens.mainscreen



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.app.domain.dataModel.NavItem
import com.messon.mess.app.presentation.viewmodel.CouponViewModel
import com.messon.mess.app.presentation.viewmodel.ScanStatVM
import com.messon.mess.app.theme.MessonTheme
import kotlinx.coroutines.launch
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.compose_multiplatform
import messon.composeapp.generated.resources.home
import messon.composeapp.generated.resources.person
import messon.composeapp.generated.resources.publicsansmedium
import messon.composeapp.generated.resources.scanner
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun MainScreen(
    onComplaintClick:()-> Unit,
    onMessMenuClick:()-> Unit,
    onBellIconClick:()->Unit,
    onNotificationsClick:()->Unit,
    onViewDetail:()-> Unit,
    onRatingClick:()-> Unit,
    onDayPolicyClick:()-> Unit,
    onMessTimingClick:()-> Unit,
    onWindowTimingClick:()-> Unit,
    onPrefAnalyticsClick:()-> Unit,
    onScanAnalyticsClick:()-> Unit,
    onCouponStatusClick: () -> Unit,
    couponViewModel: CouponViewModel,
    scanStatVM: ScanStatVM
){
    val pageCount = 3
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pageCount })
    val coroutineScope = rememberCoroutineScope()
    var selectedItemIndex by remember { mutableStateOf(0) }
    val navItemList=listOf<NavItem>(
        NavItem(icon = Res.drawable.home,"Home"),
        NavItem(icon = Res.drawable.scanner,"Scanner"),
        NavItem(icon = Res.drawable.person,"Profile")
    )
    Scaffold(
        modifier= Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                tonalElevation =20.dp,
                modifier = Modifier.height(80.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(topStart =20.dp, topEnd = 20.dp)
                    )
                    .shadow(
                        elevation =20.dp,
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),

                containerColor =MaterialTheme.colorScheme.surface
            ) {
                navItemList.forEachIndexed { index, item ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        selected = pagerState.currentPage==index,
                        icon = {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = "",
                                modifier = Modifier.size(22.dp)

                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(Res.font.publicsansmedium) )
                            )
                        }
                    )
                }
            }
        }

    ) {innerPadding->
        ContentScreen(
            modifier = Modifier.padding(top = 0.dp, bottom = innerPadding.calculateBottomPadding())
                .fillMaxSize(),
            onComplaintClick = onComplaintClick,
            onBellIconClick = onBellIconClick,
            onNotificationsClick = onNotificationsClick,
            onMessMenuClick = onMessMenuClick,
            couponViewModel = couponViewModel,
            onViewDetail = onViewDetail,
            onRatingClick = onRatingClick,
            onDayPolicyClick = onDayPolicyClick,
            onMessTimingClick = onMessTimingClick,
            onWindowTimingClick = onWindowTimingClick,
            onPrefAnalyticsClick = onPrefAnalyticsClick,
            onScanAnalyticsClick = onScanAnalyticsClick,
            onCouponStatusClick = onCouponStatusClick,
            scanStatVM = scanStatVM,
            pagerState = pagerState
        )
    }
}


@Composable
fun ContentScreen(
    modifier: Modifier,
    pagerState: PagerState,
    onComplaintClick:()-> Unit,
    onMessMenuClick:()-> Unit,
    onBellIconClick:()->Unit,
    onNotificationsClick:()->Unit,
    onViewDetail:()-> Unit,
    onRatingClick:()-> Unit,
    onDayPolicyClick:()-> Unit,
    onMessTimingClick:()-> Unit,
    onWindowTimingClick:()-> Unit,
    onPrefAnalyticsClick:()-> Unit,
    onScanAnalyticsClick:()-> Unit,
    onCouponStatusClick:()-> Unit,
    couponViewModel: CouponViewModel,
    scanStatVM: ScanStatVM
){
    Box(modifier = modifier.background(MaterialTheme.colorScheme.background)){
        HorizontalPager(
            state=pagerState,
            modifier = Modifier.fillMaxSize()
        ){pageIndex->
            when(pageIndex){
                0-> HomeScreen(
                    modifier = Modifier,
                    onBellIconClick = onBellIconClick,
                    onComplaintsClick = onComplaintClick,
                    onRatingsClick = onRatingClick,
                    onNotificationClick = onNotificationsClick,
                    onMessMenuClick = onMessMenuClick,
                    onDayPolicyClick = onDayPolicyClick,
                    onMessTimingClick = onMessTimingClick,
                    onWindowTimingClick = onWindowTimingClick,
                    onPrefAnalyticsClick = onPrefAnalyticsClick,
                    onScanAnalyticsClick = onScanAnalyticsClick,
                    onCouponStatusClick =onCouponStatusClick ,
                    scanStatVM = scanStatVM
                )
                1-> ScannerScreen(
                    modifier = Modifier,
                    couponViewModel = couponViewModel,
                    onViewDetail = onViewDetail
                )
            }
        }
    }

}