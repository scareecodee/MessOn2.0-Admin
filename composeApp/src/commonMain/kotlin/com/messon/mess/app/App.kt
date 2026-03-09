package com.messon.mess.app
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.messon.mess.app.domain.auth.GoogleAuth
import com.messon.mess.app.presentation.ui.screens.mess.CreateMenuScreen
import com.messon.mess.app.presentation.ui.screens.mess.ViewMessMenuScreen


@Composable
fun App(
    googleAuth: GoogleAuth,
) {
MaterialTheme {
    val navController=rememberNavController()

//    AppNav(
//        navController=navController,
//        googleAuth = googleAuth,
//    )
//    ViewMessMenuScreen(
//        onBackBtnClick = {}
//    )

    CreateMenuScreen()
//    ViewMessMenuScreen(
//        onBackBtnClick ={}
//    )
}


}
