package com.messon.mess.app.presentation.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.messon.mess.app.domain.auth.AuthSecureStorage
import com.messon.mess.app.domain.auth.GoogleAuth
import com.messon.mess.app.presentation.ui.screens.auth.SignInRoute
import com.messon.mess.app.presentation.ui.screens.mainscreen.ComplaintScreen
import com.messon.mess.app.presentation.ui.screens.mainscreen.MainScreen
import com.messon.mess.app.presentation.ui.screens.mainscreen.NotificationScreen
import com.messon.mess.app.presentation.viewmodel.AuthViewModel
import com.messon.mess.app.presentation.viewmodel.CouponViewModel
import com.messon.mess.app.theme.enterLeft
import com.messon.mess.app.theme.exitRight
import com.messon.mess.app.utils.Screen


@Composable
fun AppNav(
    navController: NavHostController,
    viewModel: AuthViewModel,
    googleAuth: GoogleAuth,
    authSecureStorage: AuthSecureStorage,
    couponViewModel: CouponViewModel
){
    var sessionChecked by remember { mutableStateOf(false) }
    var isSessionValid by remember{ mutableStateOf(false) }

    LaunchedEffect(Unit){
        isSessionValid=authSecureStorage.isSessionValid()
        sessionChecked=true
    }

    if(!sessionChecked)
    {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
        return
    }
    NavHost(
        navController = navController,
        startDestination = if(isSessionValid) Screen.MainScreen.route else Screen.LoginScreen.route
    ){
        composable(
            route = Screen.LoginScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            SignInRoute(
                viewModel = viewModel,
                onLoginSuccess = { navController.navigate(Screen.MainScreen.route) },
                googleAuth = googleAuth
            )

        }

        composable(
            route=Screen.MainScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            MainScreen(
                onComplaintClick = {
                    navController.navigate(Screen.ComplaintScreen.route){
                        launchSingleTop=true
                    }
                },
                onReviewsClick = {},
                onNotificationsClick = {
                    navController.navigate(Screen.NotificationScreen.route){
                        launchSingleTop=true
                    }
                },
                onBellIconClick = {},
                onMessMenuClick = {},
                couponViewModel = couponViewModel
            )
        }

        composable(
            route=Screen.ComplaintScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            ComplaintScreen(
                modifier = Modifier,
                onBackBtnClick = {
                    navController.popBackStack()
                },
                onMarkAsReadBtnClick = {}
            )
        }


        composable(
            route=Screen.NotificationScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {
            NotificationScreen(
                modifier = Modifier,
                onBackBtnClick = {
                    navController.popBackStack()
                },
                onFabClick = {}
            )
        }

    }
}
