package com.example.androidschoolproject.ui

import com.example.androidschoolproject.data.LocalWeatherDataProvider
import com.example.androidschoolproject.model.WeatherCity

data class WeatherUiState(
    val cityList: List<WeatherCity> = emptyList(),
    val currentCity: WeatherCity = LocalWeatherDataProvider.defaultWeather,
    // val localCity: WeatherCity = LocalWeatherDataProvider.defaultWeather,
    val localCity: String = "",
    val countries: String = "",
    val isShowingHomepage: Boolean = true,
    val isShowingAddCityBox: Boolean = false,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val key: String = "",
)
