package com.messon.mess.app.presentation.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.messon.mess.app.domain.auth.GoogleAuth
import com.messon.mess.app.presentation.ui.screens.auth.SignInRoute
import com.messon.mess.app.presentation.ui.screens.college.CollegeListScreen
import com.messon.mess.app.presentation.ui.screens.mainscreen.MainScreen
import com.messon.mess.app.presentation.ui.screens.mess.CreateMenuScreen
import com.messon.mess.app.presentation.ui.screens.mess.MessRuleScreen
import com.messon.mess.app.presentation.ui.screens.mess.ViewMessMenuScreen
import com.messon.mess.app.presentation.ui.state.AuthState
import com.messon.mess.app.presentation.viewmodel.AuthViewModel
import com.messon.mess.app.presentation.viewmodel.CollegeScreenVM
import com.messon.mess.app.presentation.viewmodel.CouponViewModel
import com.messon.mess.app.presentation.viewmodel.MenuCreateVM
import com.messon.mess.app.presentation.viewmodel.MenuFetchVM
import com.messon.mess.app.presentation.viewmodel.MessRuleVM
import com.messon.mess.app.theme.CustomColors
import com.messon.mess.app.theme.enterLeft
import com.messon.mess.app.theme.exitRight
import com.messon.mess.app.utils.Screen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNav(
    navController: NavHostController,
    googleAuth: GoogleAuth
) {
    val authViewModel: AuthViewModel = koinViewModel()
    val couponViewModel: CouponViewModel=koinViewModel()


    val authState by authViewModel.uiState.collectAsStateWithLifecycle()

    var hasNavigated by rememberSaveable{ mutableStateOf(false) }

    LaunchedEffect(authState) {

        if (hasNavigated) return@LaunchedEffect

        when (authState) {

            is AuthState.Success -> {
                hasNavigated = true
                navController.navigate(Screen.MainScreen.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }

            AuthState.Unauthenticated -> {
                hasNavigated = true
                navController.navigate(Screen.CollegeListScreen.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }

            else -> Unit
        }
    }

    if (authState is AuthState.Idle || authState is AuthState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = CustomColors.RichCerulean,
                strokeCap = StrokeCap.Round
            )
        }
        return
    }

    NavHost(
        navController = navController,
        startDestination = Screen.CollegeListScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {

        composable(
            route = Screen.LoginScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {

            SignInRoute(
                viewModel = authViewModel,
                googleAuth = googleAuth,
                onLoginSuccess = {
                    navController.navigate(Screen.MainScreen.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = Screen.CollegeListScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ) {

            val collegeVM: CollegeScreenVM = koinViewModel()

            CollegeListScreen(
                collegeScreenVM = collegeVM,
                onBackClick = {},
                onCardClick = {
                    navController.navigate(Screen.LoginScreen.route) {
                        launchSingleTop = true
                    }
                }
            )

        }


        composable (
            route=Screen.MainScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            MainScreen(
                onReviewsClick = {},
                onComplaintClick = {
                },
                onMessMenuClick = {
                    navController.navigate(Screen.MenuCreateScreen.route){
                        launchSingleTop=true
                    }
                },
                onBellIconClick ={
                    navController.navigate(Screen.MessRuleScreen.route)
                },
                onNotificationsClick = {
                    navController.navigate(Screen.ViewMessMenuScreen.route)
                },
                couponViewModel = couponViewModel
            )
        }

        composable (
            route=Screen.MenuCreateScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            val menuCreateVM: MenuCreateVM=koinViewModel()
            CreateMenuScreen(
                menuCreateVM = menuCreateVM
            )
        }


        composable (
            route=Screen.ViewMessMenuScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            val menuFetchVM: MenuFetchVM=koinViewModel()
            ViewMessMenuScreen(
                onBackBtnClick = {navController.popBackStack()},
                menuFetchVM = menuFetchVM
            )
        }

        composable (
            route=Screen.MessRuleScreen.route,
            enterTransition = enterLeft,
            exitTransition = exitRight
        ){
            val messRuleVM: MessRuleVM=koinViewModel()
            MessRuleScreen(
                onBackBtnClick = {navController.popBackStack()},
                messRuleVM =messRuleVM
            )
        }

    }
}