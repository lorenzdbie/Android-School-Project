package com.example.androidschoolproject.data

import com.example.androidschoolproject.model.WeatherCity
import kotlinx.coroutines.flow.Flow

interface WeatherCityRepository {

    fun getAllWeatherCitiesStream(): Flow<List<WeatherCity>>

    fun getWeatherCityStream(id: Int): Flow<WeatherCity?>

    suspend fun insertWeatherCity(weatherCity: WeatherCity)

    suspend fun updateWeatherCity(weatherCity: WeatherCity)

    suspend fun deleteWeatherCity(weatherCity: WeatherCity)
}
