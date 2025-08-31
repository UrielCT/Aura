package com.aura.ui.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.aura.ui.utils.Constants

@Entity(tableName = Constants.E_CITY)
data class City(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0,
    var name:String = "",
    var country:String = "",
    var lat:Double = 0.0,
    var lon:Double = 0.0,
    @Ignore var tz_id:String = ""
){
    override fun toString(): String {
        return "$name, $country"
    }
}
