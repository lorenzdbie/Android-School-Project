package com.example.androidschoolproject.data

import com.example.androidschoolproject.network.WeatherCity
import kotlinx.coroutines.flow.Flow

class OfflineWeatherCityRepository(private val weatherDao: WeatherDao) : WeatherCityRepository {
    override fun getAllWeatherCitiesStream(): Flow<List<WeatherCity>> = weatherDao.getAllWeatherCities()

    override fun getWeatherCityStream(id: Int): Flow<WeatherCity?> = weatherDao.getWeatherCity(id)

    override suspend fun insertWeatherCity(weatherCity: WeatherCity) = weatherDao.insert(weatherCity)

    override suspend fun updateWeatherCity(weatherCity: WeatherCity) = weatherDao.update(weatherCity)

    override suspend fun deleteWeatherCity(weatherCity: WeatherCity) = weatherDao.delete(weatherCity)
}
