package com.example.androidschoolproject.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.androidschoolproject.model.WeatherCity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weatherCity: WeatherCity)

    @Update
    suspend fun update(weatherCity: WeatherCity)

    @Delete
    suspend fun delete(weatherCity: WeatherCity)

    @Query("SELECT * FROM weatherCities WHERE id = :id")
    fun getWeatherCity(id: Int): Flow<WeatherCity>

    @Query("SELECT * from weatherCities ORDER BY creationTime ASC")
    fun getAllWeatherCities(): Flow<List<WeatherCity>>
}
