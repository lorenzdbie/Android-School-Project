package com.example.androidschoolproject.data

import com.example.androidschoolproject.network.City
import com.example.androidschoolproject.network.Country
import com.example.androidschoolproject.network.CountryState
import com.example.androidschoolproject.network.WeatherCityData

class FakeApiRepository : ApiRepository {
    override suspend fun getNearestCity(latitude: Double, longitude: Double): WeatherCityData {
        TODO("Not yet implemented")
    }

    override suspend fun getCountries(): List<Country> {
        TODO("Not yet implemented")
    }

    override suspend fun getState(country: String): List<CountryState> {
        TODO("Not yet implemented")
    }

    override suspend fun getCities(country: String, state: String): List<City> {
        TODO("Not yet implemented")
    }

    override suspend fun getCity(country: String, state: String, city: String): WeatherCityData {
        TODO("Not yet implemented")
    }
}
