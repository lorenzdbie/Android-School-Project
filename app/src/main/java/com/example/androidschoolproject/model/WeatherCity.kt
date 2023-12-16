package com.example.androidschoolproject.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidschoolproject.network.TableNames
import com.example.androidschoolproject.network.WeatherCityData
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TableNames.WEATHER_CITIES)
data class WeatherCity(
    @PrimaryKey(autoGenerate = true)
    @JvmField
    val id: Long = 0,
    val city: String,
    val state: String,
    val country: String,
    @Embedded
    val cityLocation: CityCoordinates,
    @Embedded
    val weather: Weather,
    @Embedded
    val pollution: Pollution,
    val creationTime: Long = System.currentTimeMillis(),
)

fun createWeatherCity(data: WeatherCityData): WeatherCity {
    val city = data.city
    val state = data.state
    val country = data.country
    val cityLocation = CityCoordinates(
        longitude = data.location.coordinates[0],
        latitude = data.location.coordinates[1],
    )
    val pollutionData = data.current.pollutionData
    val weatherData = data.current.weatherData

    val pollution = Pollution(
        timeStamp = pollutionData.timeStamp,
        aqiUsa = pollutionData.aqiUsa,
        mainUsa = pollutionData.mainUsa,
        aqiChina = pollutionData.aqiChina,
        mainChina = pollutionData.mainChina,
    )
    val weather = Weather(
        timeStamp = weatherData.timeStamp,
        temperature = weatherData.temperature,
        atmosphericPressure = weatherData.atmosphericPressure,
        humidity = weatherData.humidity,
        windSpeed = weatherData.windSpeed,
        windDirection = weatherData.windDirection,
        weatherIcon = weatherData.weatherIcon,
    )
    return WeatherCity(
        city = city,
        state = state,
        country = country,
        cityLocation = cityLocation,
        weather = weather,
        pollution = pollution,
    )
}
