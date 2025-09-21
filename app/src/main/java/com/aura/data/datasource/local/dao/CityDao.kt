package com.aura.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aura.data.model.CityEntity
import com.aura.ui.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Insert
    suspend fun addCity(cityEntity: CityEntity): Long

    @Update
    suspend fun updateCity(cityEntity: CityEntity): Int

    @Delete
    suspend fun deleteCity(cityEntity: CityEntity): Int

    @Query("SELECT * FROM ${Constants.E_CITY}")
    fun getAllCitiesRealTime(): Flow<List<CityEntity>>

    @Query("SELECT * FROM ${Constants.E_CITY} " +
            "WHERE ${Constants.P_NAME} = :name " +
            "AND ${Constants.P_COUNTRY} = :country " +
            "LIMIT 1")
    suspend fun getCityByNameAndCountry(name: String,country:String): CityEntity?
}