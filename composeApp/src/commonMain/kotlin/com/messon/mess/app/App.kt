package com.messon.mess.app
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.messon.mess.app.domain.auth.GoogleAuth
import com.messon.mess.app.presentation.ui.navigation.AppNav
import com.messon.mess.app.presentation.ui.screens.mess.ScanStatScreen
import com.messon.mess.app.presentation.ui.screens.mess.WindowTemplateScreen
import com.messon.mess.app.presentation.viewmodel.ScanStatVM
import com.messon.mess.app.presentation.viewmodel.WindowTemplateVM
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun App(
    googleAuth: GoogleAuth,
) {
MaterialTheme {
    val navController=rememberNavController()
    AppNav(
        navController=navController,
        googleAuth = googleAuth,
    )

}


}
