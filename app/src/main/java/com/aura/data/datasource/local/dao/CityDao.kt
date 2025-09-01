package com.aura.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aura.ui.models.City
import com.aura.ui.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Insert
    suspend fun addCity(city: City): Long

    @Update
    suspend fun updateCity(city: City): Int

    @Delete
    suspend fun deleteCity(city: City): Int

    @Query("SELECT * FROM ${Constants.E_CITY}")
    suspend fun getAllCities(): List<City>

    @Query("SELECT * FROM ${Constants.E_CITY}")
    fun getAllCitiesRealTime(): Flow<List<City>>

    @Query("SELECT * FROM ${Constants.E_CITY} " +
            "WHERE ${Constants.P_NAME} = :name " +
            "AND ${Constants.P_COUNTRY} = :country " +
            "LIMIT 1")
    suspend fun getCityByNameAndCountry(name: String,country:String):City?
}