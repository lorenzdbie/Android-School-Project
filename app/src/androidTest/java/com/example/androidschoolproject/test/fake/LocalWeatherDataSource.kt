package com.example.androidschoolproject.test.fake

import com.example.androidschoolproject.model.CityCoordinates
import com.example.androidschoolproject.model.Pollution
import com.example.androidschoolproject.model.Weather
import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.network.City
import com.example.androidschoolproject.network.Country
import com.example.androidschoolproject.network.CountryState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object LocalWeatherDataSource {

    fun getWeatherCityData(): Flow<List<WeatherCity>> {
        return flowOf(
            weatherCityList,
//            listOf(
//                weatherCity1,
//                weatherCity2,
//            ),
        )
    }

    fun getCountries(): List<Country> {
        return listOf(
            Country(country = "Country 1"),
            Country(country = "Country 2"),
            Country(country = "Country 3"),
        )
    }

    fun getStates(): List<CountryState> {
        return listOf(
            CountryState(state = "State 1"),
            CountryState(state = "State 2"),
            CountryState(state = "State 2"),
        )
    }

    fun getCities(): List<City> {
        return listOf(City(city = "City 1"), City(city = "City 2"), City(city = "City 3"))
    }

    fun getLocalCity(): WeatherCity {
        return localCity
    }

    private val weatherCity1 = WeatherCity(
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
        creationTime = 1,
    )
    private val weatherCity2 = WeatherCity(
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
        creationTime = 2,
    )

    private val localCity = WeatherCity(
        city = "New Local City",
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
        creationTime = 2,
    )
    val defaultWeather = flowOf(weatherCity1)

    val weatherCityList = mutableListOf(
        weatherCity1,
        weatherCity2,
    )
}
