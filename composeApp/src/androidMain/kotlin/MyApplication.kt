package com.messon.mess.app

import android.app.Application
import com.messon.mess.app.di.platformModule
import com.messon.mess.app.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import qrgenerator.AppContext

class MyApplication : Application() {
    companion object {
        lateinit var INSTANCE: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        multiplatform.network.cmptoast.AppContext.apply { set(applicationContext) }
        INSTANCE = this
        AppContext.apply { set(applicationContext) }
        startKoin {
            androidContext(this@MyApplication)
            modules(sharedModule, platformModule)
        }
    }
}