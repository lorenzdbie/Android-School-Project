package com.example.androidschoolproject.data

import android.util.Log
import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.model.createWeatherCity
import com.example.androidschoolproject.network.City
import com.example.androidschoolproject.network.Country
import com.example.androidschoolproject.network.CountryState
import com.example.androidschoolproject.network.WeatherApiService

/**
 * Repository for the API
 * @param weatherApiService the service to get the data from
 */
class OnlineApiRepository(
    private val weatherApiService: WeatherApiService,
) : ApiRepository {

    /**
     * get the nearest city to the given coordinates
     * @param latitude latitude of the location
     * @param longitude longitude of the location
     * @return the nearest city to the given coordinates
     */
    override suspend fun getNearestCity(latitude: Double, longitude: Double): WeatherCity {
        Log.d("LOCATION", "long = $longitude, lat = $latitude")
        val response = weatherApiService.getNearestCity(
            longitude = longitude,
            latitude = latitude,
        )
        return createWeatherCity(response.data)
    }

    /**
     * get all countries
     * @return list of all countries
     */
    override suspend fun getCountries(): List<Country> {
        val response = weatherApiService.getCountries()
        return response.data
    }

    /**
     * get all states of a country
     * @param country the country to get the states of
     * @return list of all states of the given country
     */
    override suspend fun getStates(country: String): List<CountryState> {
        val response = weatherApiService.getStates(
            country = country,
        )
        return response.data
    }

    /**
     * get all cities of a state
     * @param country the country of the state
     * @param state the state to get the cities of
     * @return list of all cities of the given state
     */
    override suspend fun getCities(country: String, state: String): List<City> {
        val response =
            weatherApiService.getCities(
                country = country,
                state = state,
            )
        return response.data
    }

    /**
     * get the weatherCity of a city
     * @param country the country of the city
     * @param state the state of the city
     * @param city the city to get the weather of
     * @return the weatherCity of the given city
     */
    override suspend fun getCity(country: String, state: String, city: String): WeatherCity {
        val response = weatherApiService.getCity(
            country = country,
            state = state,
            city = city,
        )
        return createWeatherCity(response.data)
    }
}
