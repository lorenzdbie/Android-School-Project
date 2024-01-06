package com.example.androidschoolproject.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * the main Data class for cities response
 * @param status status of the response
 * @param data cities data which is a list of cities
 */
@Serializable
data class CitiesResponse(
    @SerialName("status")
    val status: String,
    @SerialName("data")
    val data: List<City>,
)

/**
 * Data class for City data
 * @param city name of the city
 */
@Serializable
data class City(
    @SerialName("city")
    val city: String,
)
