package com.aura.data.model

data class Current(
    val temp_c:Float = 0f,
    val condition: Condition = Condition(),
    val wind_kph:Float = 0f
)
