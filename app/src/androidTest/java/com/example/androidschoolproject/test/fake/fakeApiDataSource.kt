package com.example.androidschoolproject.test.fake

import com.example.androidschoolproject.model.CityLocation
import com.example.androidschoolproject.model.Pollution
import com.example.androidschoolproject.model.Weather
import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.model.createWeatherCity
import com.example.androidschoolproject.network.CitiesResponse
import com.example.androidschoolproject.network.City
import com.example.androidschoolproject.network.CountriesResponse
import com.example.androidschoolproject.network.Country
import com.example.androidschoolproject.network.CountryState
import com.example.androidschoolproject.network.CountryStatesResponse
import com.example.androidschoolproject.network.CurrentWeather
import com.example.androidschoolproject.network.WeatherCityData
import com.example.androidschoolproject.network.WeatherCityResponse

object FakeApiDataSource {

    val countries = listOf(
        Country(country = "Country 1"),
        Country(country = "Country 2"),
        Country(country = "Country 3"),
    )
    val states = listOf(
        CountryState(state = "State 1"),
        CountryState(state = "State 2"),
        CountryState(state = "State 3"),
    )

    val cities = listOf(City(city = "City 1"), City(city = "City 2"), City(city = "City 3"))

    val countriesResponse = CountriesResponse(
        status = "success",
        data = listOf(
            Country(country = "Country 1"),
            Country(country = "Country 2"),
            Country(country = "Country 3"),
        ),
    )

    val statesResponse = CountryStatesResponse(
        status = "success",
        data = listOf(
            CountryState(state = "State 1"),
            CountryState(state = "State 2"),
            CountryState(state = "State 3"),
        ),
    )

    val citiesResponse = CitiesResponse(
        status = "success",
        data = listOf(City(city = "City 1"), City(city = "City 2"), City(city = "City 3")),
    )

    val cityResponse = WeatherCityResponse(
        status = "success",
        data = WeatherCityData(
            city = "city",
            state = "state",
            country = "country",
            location = CityLocation(
                type = "point",
                coordinates = doubleArrayOf(3.80873543079248, 51.1501360922306),
            ),
            current = CurrentWeather(
                pollutionData = Pollution(
                    timeStamp = "2023-10-28T08:00:00.000Z",
                    aqiUsa = 23,
                    mainUsa = "o3",
                    aqiChina = 18,
                    mainChina = "o3",
                ),
                weatherData = Weather(
                    timeStamp = "2023-10-28T09:00:00.000Z",
                    temperature = 12.0,
                    atmosphericPressure = 993,
                    humidity = 81,
                    windSpeed = 5.36f,
                    windDirection = 90,
                    weatherIcon = "04d",
                ),
            ),
        ),
    )

    val localCity: WeatherCity = createWeatherCity(cityResponse.data)
}
