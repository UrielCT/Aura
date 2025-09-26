package com.aura.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("location") val location: CityEntity = CityEntity(),
    @SerializedName("current") val current: Current = Current()
)
