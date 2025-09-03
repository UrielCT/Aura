package com.aura.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aura.ui.utils.Constants

@Entity(tableName = Constants.E_WEATHER)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    val temp_c: Float = 0f,
    val description: String = "",
    val wind_kph: Float = 0f,
    val iconHttps: String = "",
    val cityId: Long = 0
)
