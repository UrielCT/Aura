package com.aura.ui.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aura.ui.utils.Constants

@Entity(tableName = Constants.E_WEATHER)
data class Weather(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    val temp_c: Float = 0f,
    val description: String = "",
    val wind_kph: Float = 0f,
    val iconHttps: String = "",
    val cityId: Long = 0
)
