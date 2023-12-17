package com.example.androidschoolproject.data

import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.network.City
import com.example.androidschoolproject.network.Country
import com.example.androidschoolproject.network.CountryState

interface ApiRepository {
    suspend fun getNearestCity(latitude: Double, longitude: Double): WeatherCity

    suspend fun getCountries(): List<Country>

    suspend fun getStates(country: String): List<CountryState>

    suspend fun getCities(country: String, state: String): List<City>

    suspend fun getCity(country: String, state: String, city: String): WeatherCity
}
