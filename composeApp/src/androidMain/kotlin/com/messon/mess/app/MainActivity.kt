package com.messon.mess.app
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.LocalActivity
import com.messon.mess.app.domain.auth.GoogleAuth
import org.koin.compose.koinInject
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


            App(
                googleAuth = googleAuth,
           )
        }
    }
}