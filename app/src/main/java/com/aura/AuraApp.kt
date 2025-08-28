package com.aura

import android.app.Application
import com.aura.ui.di.utilsModule
import com.aura.ui.screens.weather.di.remoteDataSourceModule
import com.aura.ui.screens.weather.di.weatherModule
import org.koin.core.context.startKoin

class AuraApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            modules(weatherModule, utilsModule, remoteDataSourceModule)
        }
    }
}