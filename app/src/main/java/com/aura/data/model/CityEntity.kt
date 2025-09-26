package com.aura.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aura.ui.utils.Constants
import com.google.gson.annotations.SerializedName

@Entity(tableName = Constants.E_CITY)
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    @SerializedName(value = "id") var id:Long = 0,
    @SerializedName(value = "name") var name:String = "",
    @SerializedName(value = "country") var country:String = "",
    @SerializedName(value = "lat") var lat:Double = 0.0,
    @SerializedName(value = "lon") var lon:Double = 0.0,
){
    override fun toString(): String {
        return "$name, $country"
    }
}
