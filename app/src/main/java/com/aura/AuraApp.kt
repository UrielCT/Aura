package com.aura

import android.app.Application
import com.aura.di.componentsModule
import com.aura.di.utilsModule
import com.aura.di.databaseModule
import com.aura.di.networkModule
import com.aura.di.repositoryModule
import com.aura.di.useCaseModule
import com.aura.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AuraApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@AuraApp)
            modules(
                utilsModule,
                networkModule,
                componentsModule,
                viewModelModule,
                databaseModule,
                repositoryModule,
                useCaseModule
            )
        }
    }
}