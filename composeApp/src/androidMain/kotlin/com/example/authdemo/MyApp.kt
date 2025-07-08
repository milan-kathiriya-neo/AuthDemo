package com.example.authdemo

import android.app.Application
import com.example.authdemo.di.androidAppModule
import com.example.authdemo.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp:Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            modules(appModule, androidAppModule)
        }
    }
}