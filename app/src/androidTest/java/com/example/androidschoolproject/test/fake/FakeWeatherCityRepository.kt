package com.example.androidschoolproject.test.fake

import com.example.androidschoolproject.data.WeatherCityRepository
import com.example.androidschoolproject.model.WeatherCity
import kotlinx.coroutines.flow.Flow

class FakeWeatherCityRepository : WeatherCityRepository {
    override fun getAllWeatherCitiesStream(): Flow<List<WeatherCity>> =
        LocalWeatherDataSource.getWeatherCityData()

    override fun getWeatherCityStream(id: Int): Flow<WeatherCity?> =
        LocalWeatherDataSource.defaultWeather

    override suspend fun insertWeatherCity(weatherCity: WeatherCity) {
        LocalWeatherDataSource.weatherCityList.add(
            weatherCity,
        )
    }

    override suspend fun updateWeatherCity(weatherCity: WeatherCity) {
        LocalWeatherDataSource.weatherCityList
    }

    override suspend fun deleteWeatherCity(weatherCity: WeatherCity) {
        LocalWeatherDataSource.weatherCityList.remove(
            weatherCity,
        )
    }
}
//
