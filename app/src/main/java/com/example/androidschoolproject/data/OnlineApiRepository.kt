package com.example.androidschoolproject.data

import com.example.androidschoolproject.network.City
import com.example.androidschoolproject.network.Country
import com.example.androidschoolproject.network.CountryState
import com.example.androidschoolproject.network.WeatherApi
import com.example.androidschoolproject.network.WeatherCity
import com.example.androidschoolproject.network.createWeatherCity

class OnlineApiRepository(private val apiKey: String): ApiRepository {
    override suspend fun getNearestCity(latitude: Double, longitude: Double): WeatherCity {
        val response = WeatherApi.retrofitService.getNearestCity( longitude = longitude,
            latitude = latitude,
            apiKey = apiKey)
        return createWeatherCity(response)
    }

    override suspend fun getCountries(): List<Country> {
        val response = WeatherApi.retrofitService.getCountries(apiKey = apiKey)
        return response.data
    }

    override suspend fun getState(country: String): List<CountryState> {
        val response = WeatherApi.retrofitService.getStates(country = country, apiKey = apiKey)
        return response.data
    }

    override suspend fun getCities(country: String, state: String): List<City> {
        val response = WeatherApi.retrofitService.getCities(country = country, state = state, apiKey = apiKey)
        return response.data
    }

    override suspend fun getCity(country: String, state: String, city: String): WeatherCity {
        val response = WeatherApi.retrofitService.getCity(country = country, state = state, city = city, apiKey = apiKey)
        return createWeatherCity(response)
    }

}