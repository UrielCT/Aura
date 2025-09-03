package com.aura.ui.model

import com.aura.domain.model.City

data class CityUiModel(
    val id: Long,
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double
){
    override fun toString(): String = "$name, $country"

    fun toCity(): City {
        return City(
            id = id,
            name = name,
            country = country,
            lat = lat,
            lon = lon
        )
    }
}