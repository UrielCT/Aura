package com.aura.di

import com.aura.data.datasource.firebase.FirebaseRemoteConfigProvider
import com.aura.domain.remote.RemoteConfigProvider
import com.aura.domain.repository.WeatherRepository
import com.aura.data.repository.WeatherRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<RemoteConfigProvider> { FirebaseRemoteConfigProvider() }

    single<WeatherRepository> {
        WeatherRepositoryImpl(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            context = androidContext(),
            remoteConfigProvider = get()
        )
    }
}
