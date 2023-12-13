package com.example.androidschoolproject.network

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
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
@Entity(tableName = TableNames.LOCATION)
data class CityCoordinates(
    @PrimaryKey(autoGenerate = true)
    @JvmField
    @ColumnInfo(name = "locationID")
    val id: Int = 0,
    val longitude: Double,
    val latitude: Double,
)

@Serializable
@Entity(tableName = TableNames.POLLUTION)
data class Pollution(
    @PrimaryKey(autoGenerate = true)
    @JvmField
    @ColumnInfo(name = "pollutionID")
    val id: Int = 0,
    @SerialName("ts")
    @ColumnInfo(name = "pollutionTimeStamp")
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
@Entity(tableName = TableNames.WEATHER)
data class Weather(
    @PrimaryKey(autoGenerate = true)
    @JvmField
    @ColumnInfo(name = "weatherID")
    val id: Int = 0,
    @SerialName("ts")
    @ColumnInfo(name = "weatherTimeStamp")
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
@Entity(tableName = TableNames.WEATHER_CITIES)
data class WeatherCity(
    @PrimaryKey
    @JvmField
    val id: String = generateUniqueId(),
    val city: String,
    val state: String,
    val country: String,
    @Embedded
    val cityLocation: CityCoordinates,
    @Embedded
    val weather: Weather,
    @Embedded
    val pollution: Pollution,
) {
    companion object {
        private var idCounter = 0
        private const val uniqueIdPrefix = "ID"

        private fun generateUniqueId(): String {
            idCounter++
            return "$uniqueIdPrefix$idCounter"
        }
    }
}

fun createWeatherCity(data: WeatherCityData): WeatherCity {
      val city = data.city
    val state = data.state
    val country = data.country
    val cityLocation = CityCoordinates(longitude = data.location.coordinates[0], latitude = data.location.coordinates[1])
    val pollutionData = data.current.pollutionData
    val weatherData = data.current.weatherData

    val pollution = Pollution(timeStamp = pollutionData.timeStamp, aqiUsa = pollutionData.aqiUsa, mainUsa = pollutionData.mainUsa, aqiChina = pollutionData.aqiChina, mainChina = pollutionData.mainChina)
    val weather = Weather(timeStamp = weatherData.timeStamp, temperature = weatherData.temperature, atmosphericPressure = weatherData.atmosphericPressure, humidity = weatherData.humidity, windSpeed = weatherData.windSpeed, windDirection = weatherData.windDirection, weatherIcon = weatherData.weatherIcon)

    return WeatherCity(city = city, state = state, country = country, cityLocation = cityLocation, weather = weather, pollution = pollution)
}
