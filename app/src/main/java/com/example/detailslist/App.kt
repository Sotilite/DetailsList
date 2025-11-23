package com.example.detailslist

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.detailslist.di.characterFeatureModule
import com.example.detailslist.di.dbModule
import com.example.detailslist.di.mainModule
import com.example.detailslist.di.networkModule
import com.example.detailslist.di.profileModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App: Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(mainModule, characterFeatureModule, networkModule, dbModule, profileModule)
        }
    }
}