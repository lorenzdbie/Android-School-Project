package com.example.androidschoolproject.data

import com.example.androidschoolproject.model.WeatherCity
import kotlinx.coroutines.flow.Flow

/**
 * Interface for WeatherCityRepository
 */
interface WeatherCityRepository {

    /**
     * get all weather cities from the database as a stream
     */
    fun getAllWeatherCitiesStream(): Flow<List<WeatherCity>>

    /**
     * get a weather city from the database as a stream
     * @param id id of the weather city
     */
    fun getWeatherCityStream(id: Int): Flow<WeatherCity?>

    /**
     * insert a weather city into the database
     * @param weatherCity the weather city to insert
     */
    suspend fun insertWeatherCity(weatherCity: WeatherCity)

    /**
     * update a weather city in the database
     * @param weatherCity the weather city to update
     */
    suspend fun updateWeatherCity(weatherCity: WeatherCity)

    /**
     * delete a weather city from the database
     * @param weatherCity the weather city to delete
     */
    suspend fun deleteWeatherCity(weatherCity: WeatherCity)
}
