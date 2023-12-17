package com.example.androidschoolproject.test.fake

import com.example.androidschoolproject.data.ApiRepository
import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.network.City
import com.example.androidschoolproject.network.Country
import com.example.androidschoolproject.network.CountryState

class FakeApiRepository : ApiRepository {
    override suspend fun getNearestCity(latitude: Double, longitude: Double): WeatherCity {
        return LocalWeatherDataSource.getLocalCity()
    }

    override suspend fun getCountries(): List<Country> {
        return LocalWeatherDataSource.getCountries()
    }

    override suspend fun getStates(country: String): List<CountryState> {
        return LocalWeatherDataSource.getStates()
    }

    override suspend fun getCities(country: String, state: String): List<City> {
        return LocalWeatherDataSource.getCities()
    }

    override suspend fun getCity(country: String, state: String, city: String): WeatherCity {
        return LocalWeatherDataSource.getLocalCity()
    }
}
