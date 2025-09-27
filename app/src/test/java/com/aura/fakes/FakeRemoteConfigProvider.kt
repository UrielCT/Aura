package com.aura.fakes

import com.aura.domain.remote.RemoteConfigProvider

class FakeRemoteConfigProvider(private val version: String = "1.0.0") : RemoteConfigProvider {
    override fun getMinVersion(): String = version
}
