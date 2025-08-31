package com.aura

import android.app.Application
import com.aura.ui.di.componentsModule
import com.aura.ui.di.utilsModule
import com.aura.ui.screens.cities.di.citiesModule
import com.aura.ui.screens.weather.di.remoteDataSourceModule
import com.aura.ui.screens.weather.di.weatherModule
import com.aura.ui.utils.localDatasourceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AuraApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@AuraApp)
            modules(weatherModule, utilsModule, remoteDataSourceModule,
                localDatasourceModule, componentsModule, citiesModule)
        }
    }
}