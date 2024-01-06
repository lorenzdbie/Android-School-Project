package com.example.androidschoolproject.network

import com.example.androidschoolproject.model.Pollution
import com.example.androidschoolproject.model.Weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * the main Data class for weather city response
 * @param status status of the response
 * @param data weather city data
 */
@Serializable
data class WeatherCityResponse(
    @SerialName("status")
    val status: String,
    @SerialName("data")
    val data: WeatherCityData,
)

/**
 * Data class for weather city data
 * @param city name of the city
 * @param state name of the state
 * @param country name of the country
 * @param location location of the city
 * @param current current weather data
 */
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

/**
 * Table names for on device database
 * @param LOCATION name of the location table
 * @param POLLUTION name of the pollution table
 * @param WEATHER name of the weather table
 * @param WEATHER_CITIES name of the weather cities table
 */
object TableNames {
    const val LOCATION = "location"
    const val POLLUTION = "pollution"
    const val WEATHER = "weather"
    const val WEATHER_CITIES = "weatherCities"
}

/**
 *  Data class for cityLocation
 *  @param type type of the location
 *  @param coordinates coordinates of the location
 */
@Serializable
data class CityLocation(
    @SerialName("type")
    val type: String,
    @SerialName("coordinates")
    val coordinates: DoubleArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CityLocation

        if (!coordinates.contentEquals(other.coordinates)) return false

        return true
    }

    override fun hashCode(): Int {
        return coordinates.contentHashCode()
    }
}

/**
 * Data class for current weather data
 * @param pollutionData pollution data
 * @param weatherData weather data
 */
@Serializable
data class CurrentWeather(
    @SerialName("pollution")
    val pollutionData: Pollution,
    @SerialName("weather")
    val weatherData: Weather,
)
