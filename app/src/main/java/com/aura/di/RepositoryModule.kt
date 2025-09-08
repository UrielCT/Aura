package com.aura.di

import com.aura.domain.repository.WeatherRepository
import com.aura.domain.repository.WeatherRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get(), get(), get(), get(), get(), get()) }
}
