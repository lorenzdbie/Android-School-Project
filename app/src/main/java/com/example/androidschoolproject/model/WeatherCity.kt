package com.example.androidschoolproject.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidschoolproject.network.TableNames
import com.example.androidschoolproject.network.WeatherCityData
import kotlinx.serialization.Serializable

/**
 * Data class for WeatherCity
 * @param id id of the city, auto generated
 * @param city name of the city
 * @param state name of the state
 * @param country name of the country
 * @param cityLocation location of the city in coordinates
 * @param weather weather data of the city
 * @param pollution pollution data of the city
 * @param creationTime time of creation of the WeatherCity object
 */
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

/**
 * function to convert WeatherCityData to WeatherCity
 * @param data WeatherCityData to be converted
 * @return WeatherCity converted from WeatherCityData
 */
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
