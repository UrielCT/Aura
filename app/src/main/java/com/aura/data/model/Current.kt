package com.aura.data.model

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("temp_c") val temp_c:Float = 0f,
    @SerializedName("condition") val condition: Condition = Condition(),
    @SerializedName("wind_kph") val wind_kph:Float = 0f
)
