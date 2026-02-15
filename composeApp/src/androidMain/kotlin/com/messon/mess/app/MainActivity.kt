package com.messon.mess.app
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.messon.app.App
import androidx.activity.compose.LocalActivity
import com.messon.mess.app.domain.auth.AuthSecureStorage
import com.messon.mess.app.domain.auth.GoogleAuth
import com.messon.mess.app.presentation.viewmodel.AuthViewModel
import com.messon.mess.app.presentation.viewmodel.CouponViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        setContent {
            val activity = LocalActivity.current as Activity
            val googleAuth: GoogleAuth = koinInject { parametersOf(activity) }
            val authSecureStorage: AuthSecureStorage = koinInject()
            val authViewModel: AuthViewModel = koinViewModel<AuthViewModel>()
            val couponViewModel: CouponViewModel=koinViewModel<CouponViewModel>()

            App(
                authViewModel = authViewModel,
                authSecureStorage =authSecureStorage,
                googleAuth = googleAuth,
                couponViewModel = couponViewModel
            )
        }
    }
}