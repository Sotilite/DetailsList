package com.example.detailslist

import android.app.Application
import com.example.detailslist.di.characterFeatureModule
import com.example.detailslist.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(mainModule, characterFeatureModule)
        }
    }
}