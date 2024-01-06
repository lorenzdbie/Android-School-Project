package com.example.androidschoolproject.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * the main Data class for countries response
 * @param status status of the response
 * @param data countries data which is a list of countries
 */
@Serializable
data class CountriesResponse(
    @SerialName("status")
    val status: String,
    @SerialName("data")
    val data: List<Country>,
)

/**
 * Data class for Country data
 * @param country name of the country
 */
@Serializable
data class Country(
    @SerialName("country")
    val country: String,
)
