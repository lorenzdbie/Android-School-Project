package com.example.androidschoolproject.data

import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.network.City
import com.example.androidschoolproject.network.Country
import com.example.androidschoolproject.network.CountryState

/**
 *  Repository interface for the API
 */
interface ApiRepository {

    /**
     * get the nearest city to the given coordinates
     * @param latitude latitude of the location
     * @param longitude longitude of the location
     * @return the nearest city to the given coordinates
     */
    suspend fun getNearestCity(latitude: Double, longitude: Double): WeatherCity

    /**
     * get all countries
     * @return list of all countries
     */
    suspend fun getCountries(): List<Country>

    /**
     * get all states of a country
     * @param country the country to get the states of
     * @return list of all states of the given country
     */
    suspend fun getStates(country: String): List<CountryState>

    /**
     * get all cities of a state
     * @param country the country of the state
     * @param state the state to get the cities of
     * @return list of all cities of the given state
     */
    suspend fun getCities(country: String, state: String): List<City>

    /**
     * get a city
     * @param country the country of the city
     * @param state the state of the city
     * @param city the city to get
     * @return the weatherCity
     */
    suspend fun getCity(country: String, state: String, city: String): WeatherCity
}
