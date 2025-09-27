package com.aura.data.datasource.firebase

import com.aura.data.repository.WeatherRepositoryImpl
import com.aura.domain.remote.RemoteConfigProvider
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class FirebaseRemoteConfigProvider(
    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
) : RemoteConfigProvider {

    init {
        remoteConfig.setConfigSettingsAsync(
            remoteConfigSettings { minimumFetchIntervalInSeconds = 3600 }
        )
        remoteConfig.fetchAndActivate()
    }

    override fun getMinVersion(): String {
        return remoteConfig.getString(WeatherRepositoryImpl.MIN_VERSION)
    }
}