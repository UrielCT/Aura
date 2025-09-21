package com.aura.domain.usecase

import com.aura.domain.repository.WeatherRepository

class CanAccessToAppUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(): Boolean {
        val currentVersion = repository.getCurrentVersion()
        val minAllowedVersion = repository.getMinAllowedVersion()

        val maxLength = maxOf(currentVersion.size, minAllowedVersion.size)

        for (i in 0 until maxLength) {
            val currentPart = currentVersion.getOrNull(i) ?: 0
            val minPart = minAllowedVersion.getOrNull(i) ?: 0

            if (currentPart > minPart) return true
            if (currentPart < minPart) return false
        }
        return true
    }
}
