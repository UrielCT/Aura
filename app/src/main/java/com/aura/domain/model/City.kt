package com.aura.domain.model

import com.aura.data.model.CityEntity
import com.aura.ui.model.CityUiModel

data class City(
    val id: Long,
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,
){
    fun toCityUiModel(): CityUiModel = CityUiModel(
        id = id,
        name = name,
        country = country,
        lat = lat,
        lon = lon
    )

    fun toEntity() = CityEntity(
        id = id,
        name = name,
        country = country,
        lat = lat,
        lon = lon
    )
}
