package com.example.androidschoolproject.data

import com.example.androidschoolproject.model.Location
import com.example.androidschoolproject.model.Pollution
import com.example.androidschoolproject.model.Weather
import com.example.androidschoolproject.model.WeatherCity

object LocalWeatherDataProvider {

    val defaultWeather = getWeatherCityData()[0]
    fun getWeatherCityData(): List<WeatherCity> {
        return listOf(
            WeatherCity(
                city = "Gent",
                state = "Flanders",
                country = "Belgium",
                location = Location(
                    longitude = 3.80873543079248,
                    latitude = 51.1501360922306,
                ),
                weather = Weather(
                    timeStamp = "2023-10-28T09:00:00.000Z",
                    temperature = 12f,
                    atmosphericPressure = 993f,
                    humidity = 81f,
                    windSpeed = 5.36f,
                    windDirection = 90f,
                    weatherIcon = "04d",
                ),
                pollution = Pollution(
                    timeStamp = "2023-10-28T08:00:00.000Z",
                    aqiUsa = 23f,
                    mainUsa = "o3",
                    aqiChina = 18f,
                    mainChina = "o3",
                ),
            ),
            WeatherCity(
                city = "New York City",
                state = "New York",
                country = "USA",
                location = Location(
                    longitude = -73.928596,
                    latitude = 40.694401,
                ),
                weather = Weather(
                    timeStamp = "2023-10-28T10:00:00.000Z",
                    temperature = 17f,
                    atmosphericPressure = 1016f,
                    humidity = 81f,
                    windSpeed = 4.12f,
                    windDirection = 200f,
                    weatherIcon = "02n",
                ),
                pollution = Pollution(
                    timeStamp = "2023-10-28T10:00:00.000Z",
                    aqiUsa = 43f,
                    mainUsa = "p2",
                    aqiChina = 15f,
                    mainChina = "p2",
                ),
            ),
        )
    }
}
