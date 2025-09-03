package com.aura.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aura.domain.model.City
import com.aura.ui.utils.Constants

@Entity(tableName = Constants.E_CITY)
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0,
    var name:String = "",
    var country:String = "",
    var lat:Double = 0.0,
    var lon:Double = 0.0,
){
    override fun toString(): String {
        return "$name, $country"
    }

    fun toCity() = City(
        id = id,
        name = name,
        country = country,
        lat = lat,
        lon = lon
    )
}
