package com.example.androidschoolproject.data

import android.util.Log
import com.example.androidschoolproject.network.City
import com.example.androidschoolproject.network.Country
import com.example.androidschoolproject.network.CountryState
import com.example.androidschoolproject.network.WeatherApi
import com.example.androidschoolproject.network.WeatherCityData

class OnlineApiRepository(private val apiKey: String) : ApiRepository {

    override suspend fun getNearestCity(latitude: Double, longitude: Double): WeatherCityData {
        Log.d("LOCATION", "long = $longitude, lat = $latitude")
        val response = WeatherApi.retrofitService.getNearestCity(
            longitude = longitude,
            latitude = latitude,
            apiKey = apiKey,
        )
        return response.data
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
        val response =
            WeatherApi.retrofitService.getCities(country = country, state = state, apiKey = apiKey)
        return response.data
    }

    override suspend fun getCity(country: String, state: String, city: String): WeatherCityData {
        val response = WeatherApi.retrofitService.getCity(
            country = country,
            state = state,
            city = city,
            apiKey = apiKey,
        )
        return response.data
    }
}
