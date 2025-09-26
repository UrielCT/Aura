package com.aura.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aura.ui.utils.Constants
import com.google.gson.annotations.SerializedName

@Entity(tableName = Constants.E_WEATHER)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    @SerializedName(value = "id") val id:Long = 0,
    @SerializedName(value = "temp_c") val temp_c: Float = 0f,
    @SerializedName(value = "description") val description: String = "",
    @SerializedName(value = "wind_kph") val wind_kph: Float = 0f,
    @SerializedName(value = "iconHttps") val iconHttps: String = "",
    @SerializedName(value = "cityId") val cityId: Long = 0
)
