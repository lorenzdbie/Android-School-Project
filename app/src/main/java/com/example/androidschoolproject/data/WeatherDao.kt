package com.example.androidschoolproject.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.androidschoolproject.model.WeatherCity
import kotlinx.coroutines.flow.Flow

/**
 * Interface for WeatherDao
 * This interface is used by Room to generate the queries for the database
 */
@Dao
interface WeatherDao {
    /**
     * insert a weather city into the database
     * @param weatherCity the weather city to insert
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weatherCity: WeatherCity)

    /**
     * update a weather city in the database
     * @param weatherCity the weather city to update
     */
    @Update
    suspend fun update(weatherCity: WeatherCity)

    /**
     * delete a weather city from the database
     * @param weatherCity the weather city to delete
     */
    @Delete
    suspend fun delete(weatherCity: WeatherCity)

    /**
     * get a weather city from the database as a stream
     * @param id id of the weather city
     */
    @Query("SELECT * FROM weatherCities WHERE id = :id")
    fun getWeatherCity(id: Int): Flow<WeatherCity>

    /**
     * get all weather cities from the database as a stream
     */
    @Query("SELECT * from weatherCities ORDER BY creationTime ASC")
    fun getAllWeatherCities(): Flow<List<WeatherCity>>
}
