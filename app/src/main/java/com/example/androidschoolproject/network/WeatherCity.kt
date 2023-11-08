package com.example.androidschoolproject.network

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

@Serializable
data class CityLocation(
    @SerialName("type")
    val type: String,
    @SerialName("coordinates")
    val coordinates: DoubleArray,
)

@Serializable
data class CurrentWeather(
    @SerialName("pollution")
    val pollutionData: Pollution,
    @SerialName("weather")
    val weatherData: Weather,
)

@Serializable
data class Pollution(
    @SerialName("ts")
    val timeStamp: String,
    @SerialName("aqius")
    val aqiUsa: Int,
    @SerialName("mainus")
    val mainUsa: String,
    @SerialName("aqicn")
    val aqiChina: Int,
    @SerialName("maincn")
    val mainChina: String,
)

@Serializable
data class Weather(
    @SerialName("ts")
    val timeStamp: String,
    @SerialName("tp")
    val temperature: Double,
    @SerialName("pr")
    val atmosphericPressure: Int,
    @SerialName("hu")
    val humidity: Int,
    @SerialName("ws")
    val windSpeed: Float,
    @SerialName("wd")
    val windDirection: Int,
    @SerialName("ic")
    val weatherIcon: String,
)

@Serializable
data class WeatherCity(
    @JvmField val id: String = generateUniqueId(),
    val city: String,
    val state: String,
    val country: String,
    val cityLocation: CityLocation,
    val weather: Weather,
    val pollution: Pollution,
) {
    companion object {
        private var idCounter = 0

        private fun generateUniqueId(): String {
            idCounter++
            return "ID$idCounter"
        }
    }
}

fun createWeatherCity(weatherCityResponse: WeatherCityResponse): WeatherCity {
    val data = weatherCityResponse.data

    val city = data.city
    val state = data.state
    val country = data.country
    val cityLocation = data.location
    val pollutionData = data.current.pollutionData
    val weatherData = data.current.weatherData

    val pollution = Pollution(pollutionData.timeStamp, pollutionData.aqiUsa, pollutionData.mainUsa, pollutionData.aqiChina, pollutionData.mainChina)
    val weather = Weather(weatherData.timeStamp, weatherData.temperature, weatherData.atmosphericPressure, weatherData.humidity, weatherData.windSpeed, weatherData.windDirection, weatherData.weatherIcon)

    return WeatherCity(city = city, state = state, country = country, cityLocation = cityLocation, weather = weather, pollution = pollution)
}
