package com.example.androidschoolproject.data

import com.example.androidschoolproject.network.City
import com.example.androidschoolproject.network.Country
import com.example.androidschoolproject.network.CountryState
import com.example.androidschoolproject.network.WeatherCityData

interface ApiRepository {
    suspend fun getNearestCity(latitude: Double, longitude: Double): WeatherCityData

    suspend fun getCountries(): List<Country>

    suspend fun getState(country: String): List<CountryState>

    suspend fun getCities(country: String, state: String): List<City>

    suspend fun getCity(country: String, state: String, city: String): WeatherCityData
}