package com.example.androidschoolproject.data

import com.example.androidschoolproject.network.WeatherCity
import kotlinx.coroutines.flow.Flow

class FakeWeatherCityRepository: WeatherCityRepository {
    override fun getAllWeatherCitiesStream(): Flow<List<WeatherCity>> = LocalWeatherDataProvider.getWeatherCityData()

    override fun getWeatherCityStream(id: Int): Flow<WeatherCity?>  = LocalWeatherDataProvider.defaultWeather

    override suspend fun insertWeatherCity(weatherCity: WeatherCity) {
        TODO("Not yet implemented")
    }

    override suspend fun updateWeatherCity(weatherCity: WeatherCity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteWeatherCity(weatherCity: WeatherCity) {
        TODO("Not yet implemented")
    }
}