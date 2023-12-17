package com.example.androidschoolproject.data

import android.util.Log
import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.model.createWeatherCity
import com.example.androidschoolproject.network.City
import com.example.androidschoolproject.network.Country
import com.example.androidschoolproject.network.CountryState
import com.example.androidschoolproject.network.WeatherApiService

class OnlineApiRepository(
    private val weatherApiService: WeatherApiService,
) : ApiRepository {

    override suspend fun getNearestCity(latitude: Double, longitude: Double): WeatherCity {
        Log.d("LOCATION", "long = $longitude, lat = $latitude")
        val response = weatherApiService.getNearestCity(
            longitude = longitude,
            latitude = latitude,
//            apiKey = apiKey,
        )
        return createWeatherCity(response.data)
    }

    override suspend fun getCountries(): List<Country> {
        val response = weatherApiService.getCountries(
//            apiKey = apiKey
        )
        return response.data
    }

    override suspend fun getStates(country: String): List<CountryState> {
        val response = weatherApiService.getStates(
            country = country,
//            apiKey = apiKey
        )
        return response.data
    }

    override suspend fun getCities(country: String, state: String): List<City> {
        val response =
            weatherApiService.getCities(
                country = country,
                state = state,
//                apiKey = apiKey
            )
        return response.data
    }

    override suspend fun getCity(country: String, state: String, city: String): WeatherCity {
        val response = weatherApiService.getCity(
            country = country,
            state = state,
            city = city,
//            apiKey = apiKey,
        )
        return createWeatherCity(response.data)
    }
}
