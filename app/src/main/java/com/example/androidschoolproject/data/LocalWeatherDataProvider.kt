package com.example.androidschoolproject.data

import com.example.androidschoolproject.network.CityCoordinates
import com.example.androidschoolproject.network.Pollution
import com.example.androidschoolproject.network.Weather
import com.example.androidschoolproject.network.WeatherCity

// import com.example.androidschoolproject.model.CityLocation
// import com.example.androidschoolproject.model.Pollution
// import com.example.androidschoolproject.model.Weather
// import com.example.androidschoolproject.model.WeatherCity

object LocalWeatherDataProvider {

    val defaultWeather = getWeatherCityData()[0]
    fun getWeatherCityData(): MutableList<WeatherCity> {
        return mutableListOf(
            WeatherCity(
                city = "Gent",
                state = "Flanders",
                country = "Belgium",
                cityLocation = CityCoordinates(longitude = 3.80873543079248, latitude = 51.1501360922306),
                weather = Weather(
                    timeStamp = "2023-10-28T09:00:00.000Z",
                    temperature = 12.0,
                    atmosphericPressure = 993,
                    humidity = 81,
                    windSpeed = 5.36f,
                    windDirection = 90,
                    weatherIcon = "04d",
                ),
                pollution = Pollution(
                    timeStamp = "2023-10-28T08:00:00.000Z",
                    aqiUsa = 23,
                    mainUsa = "o3",
                    aqiChina = 18,
                    mainChina = "o3",
                ),
            ),
            WeatherCity(
                city = "New York City",
                state = "New York",
                country = "USA",
                cityLocation = CityCoordinates(longitude = -73.928596, latitude = 40.694401),
                weather = Weather(
                    timeStamp = "2023-10-28T10:00:00.000Z",
                    temperature = 17.0,
                    atmosphericPressure = 1016,
                    humidity = 81,
                    windSpeed = 4.12f,
                    windDirection = 200,
                    weatherIcon = "02n",
                ),
                pollution = Pollution(
                    timeStamp = "2023-10-28T10:00:00.000Z",
                    aqiUsa = 43,
                    mainUsa = "p2",
                    aqiChina = 15,
                    mainChina = "p2",
                ),
            ),
        )
    }
}
