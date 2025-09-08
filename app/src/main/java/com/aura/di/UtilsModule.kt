package com.aura.di

import com.aura.data.mappers.DataMappers
import com.aura.domain.mappers.DomainMappers
import com.aura.ui.mappers.UiMappers
import com.aura.ui.utils.FormatUtils
import com.aura.ui.utils.IntentUtils
import com.aura.ui.utils.NetworkUtils
import org.koin.dsl.module

val utilsModule = module {
    single { FormatUtils() }
    single { NetworkUtils( get() ) }
    single { IntentUtils( get() ) }
    single { DataMappers() }
    single { DomainMappers() }
    single { UiMappers() }
}