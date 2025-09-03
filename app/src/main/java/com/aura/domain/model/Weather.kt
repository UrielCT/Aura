package com.aura.domain.model

data class Weather(
    val id: Long,
    val temp_c: Float,
    val description: String,
    val wind_kph: Float,
    val iconHttps: String,
    val cityId: Long
)