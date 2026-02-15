package com.messon.mess.app.presentation.ui.screens.mainscreen



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.app.domain.dataModel.NavItem
import com.messon.mess.app.presentation.viewmodel.CouponViewModel
import com.messon.mess.app.theme.CustomColors
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
    onReviewsClick:()-> Unit,
    onComplaintClick:()-> Unit,
    onMessMenuClick:()-> Unit,
    onBellIconClick:()->Unit,
    onNotificationsClick:()->Unit,
    couponViewModel: CouponViewModel
){

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
                modifier = Modifier.height(85.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart =20.dp, topEnd = 20.dp)
                    )
                    .shadow(
                        elevation =20.dp,
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
                ,
                containerColor = Color.White
            ) {
                navItemList.forEachIndexed { index, item ->
                    NavigationBarItem(
                        colors = NavigationBarItemColors(
                            selectedIconColor = if(selectedItemIndex==1) CustomColors.RichCerulean else CustomColors.BlackberryCream,
                            selectedTextColor = if(selectedItemIndex==1) Color.Black else CustomColors.BlackberryCream,
                            selectedIndicatorColor =if (selectedItemIndex==1) CustomColors.LightBlue else Color(0xFFF7E6ED),
                            unselectedIconColor =Color.Black,
                            unselectedTextColor = Color.Black,
                            disabledIconColor = Color.Black,
                            disabledTextColor = Color.Black

                        ),
                        onClick = {
                            selectedItemIndex=index
                        },
                        selected = selectedItemIndex==index,
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
            modifier = Modifier.padding(top = 0.dp, bottom = innerPadding.calculateBottomPadding()),
            selectedScreenIndex=selectedItemIndex,
            onReviewsClick =onReviewsClick,
            onComplaintClick = onComplaintClick,
            onBellIconClick =onBellIconClick,
            onNotificationsClick =onNotificationsClick,
            onMessMenuClick = onMessMenuClick,
            couponViewModel = couponViewModel
        )
    }
}


@Composable
fun ContentScreen(
    modifier: Modifier,
    selectedScreenIndex: Int,
    onReviewsClick:()-> Unit,
    onComplaintClick:()-> Unit,
    onMessMenuClick:()-> Unit,
    onBellIconClick:()->Unit,
    onNotificationsClick:()->Unit,
    couponViewModel: CouponViewModel
){
    when(selectedScreenIndex){
        0-> HomeScreen(
            modifier = modifier,
            profileImg = painterResource(Res.drawable.compose_multiplatform),
            greeting = "Heyy",
            onReviewsClick =onReviewsClick,
            onComplaintsClick = onComplaintClick,
            onBellIconClick =onBellIconClick,
            onNotificationsClick =onNotificationsClick,
            onMessMenuClick = onMessMenuClick
        )
        1-> ScannerScreen(
            modifier = modifier,
            couponViewModel = couponViewModel
        )
    }
}