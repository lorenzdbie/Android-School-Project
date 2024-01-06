package com.example.androidschoolproject.data

import com.example.androidschoolproject.model.WeatherCity
import kotlinx.coroutines.flow.Flow

/**
 * repository for de local database
 * @param weatherDao the weatherDao to use
 */
class OfflineWeatherCityRepository(private val weatherDao: WeatherDao) : WeatherCityRepository {
    /**
     * get all weather cities from the local database as a stream
     */
    override fun getAllWeatherCitiesStream(): Flow<List<WeatherCity>> = weatherDao.getAllWeatherCities()

    /**
     * get a weather city from the local database as a stream
     * @param id id of the weather city
     */
    override fun getWeatherCityStream(id: Int): Flow<WeatherCity?> = weatherDao.getWeatherCity(id)

    /**
     * insert a weather city into the local database
     * @param weatherCity the weather city to insert
     */
    override suspend fun insertWeatherCity(weatherCity: WeatherCity) = weatherDao.insert(weatherCity)

    /**
     * update a weather city in the local database
     * @param weatherCity the weather city to update
     */
    override suspend fun updateWeatherCity(weatherCity: WeatherCity) = weatherDao.update(weatherCity)

    /**
     * delete a weather city from the local database
     * @param weatherCity the weather city to delete
     */
    override suspend fun deleteWeatherCity(weatherCity: WeatherCity) = weatherDao.delete(weatherCity)
}
