package com.arkan.seeflix

import android.app.Application
import com.arkan.seeflix.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@App)
            modules(AppModules.modules)
        }
    }
}