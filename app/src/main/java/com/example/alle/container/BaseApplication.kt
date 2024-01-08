package com.example.alle.container

import android.app.Application
import com.example.alle.di.alleModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(applicationContext)
            modules(alleModule)
        }
    }
}