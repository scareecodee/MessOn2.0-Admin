package com.messon.mess.app.di

import android.app.Activity
import android.content.Context
import com.messon.mess.app.core.AppDispatchers
import com.messon.mess.app.data.remote.network.createHttpClient
import com.messon.mess.app.domain.auth.AuthSecureStorage
import com.messon.mess.app.domain.auth.GoogleAuth
import com.messon.mess.app.domain.auth.GoogleAuthAndroid
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

actual val platformModule = module {



    single { AppDispatchers() }

    single { createHttpClient(OkHttp.create()) }

    factory<GoogleAuth> { (activity: Activity) ->
        GoogleAuthAndroid(
            serverClientId = "238348258649-0j2o0hqbhu0pcdrg5i0j1sajg1ppaeeu.apps.googleusercontent.com",
            context = activity
        )
    }


    single {
        val context: Context = get()
        try {
            AuthSecureStorage(context)
        } catch (e: Exception) {
            context.deleteSharedPreferences("auth_prefs")
            AuthSecureStorage(context)
        }
    }

}