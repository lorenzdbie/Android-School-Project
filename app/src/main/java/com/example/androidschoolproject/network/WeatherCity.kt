package com.example.androidschoolproject.network

import com.example.androidschoolproject.model.CityLocation
import com.example.androidschoolproject.model.Pollution
import com.example.androidschoolproject.model.Weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherCityResponse(
    @SerialName("status")
    val status: String,
    @SerialName("data")
    val data: WeatherCityData,
)

@Serializable
data class WeatherCityData(
    @SerialName("city")
    val city: String,
    @SerialName("state")
    val state: String,
    @SerialName("country")
    val country: String,
    @SerialName("location")
    val location: CityLocation,
    @SerialName("current")
    val current: CurrentWeather,
)

object TableNames {
    const val LOCATION = "location"
    const val POLLUTION = "pollution"
    const val WEATHER = "weather"
    const val WEATHER_CITIES = "weatherCities"
}

@Serializable
data class CurrentWeather(
    @SerialName("pollution")
    val pollutionData: Pollution,
    @SerialName("weather")
    val weatherData: Weather,
)
