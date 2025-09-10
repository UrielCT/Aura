package com.aura.data.mappers

import com.aura.data.model.CityEntity
import com.aura.domain.model.City

class DataMappers {

    fun cityEntityToCity(cityEntity: CityEntity): City {
        return City(
            id = cityEntity.id,
            name = cityEntity.name,
            country = cityEntity.country,
            lat = cityEntity.lat,
            lon = cityEntity.lon
        )
    }

}