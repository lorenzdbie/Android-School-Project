package com.example.androidschoolproject.test.fake

import com.example.androidschoolproject.network.CitiesResponse
import com.example.androidschoolproject.network.CountriesResponse
import com.example.androidschoolproject.network.CountryStatesResponse
import com.example.androidschoolproject.network.WeatherApiService
import com.example.androidschoolproject.network.WeatherCityResponse

class FakeApiService : WeatherApiService {

    override suspend fun getNearestCity(latitude: Double, longitude: Double): WeatherCityResponse {
        return FakeApiDataSource.cityResponse
    }

    override suspend fun getCountries(): CountriesResponse {
        return FakeApiDataSource.countriesResponse
    }

    override suspend fun getStates(country: String): CountryStatesResponse {
        return FakeApiDataSource.statesResponse
    }

    override suspend fun getCities(state: String, country: String): CitiesResponse {
        return FakeApiDataSource.citiesResponse
    }

    override suspend fun getCity(
        city: String,
        state: String,
        country: String,
    ): WeatherCityResponse {
        return FakeApiDataSource.cityResponse
    }
}
