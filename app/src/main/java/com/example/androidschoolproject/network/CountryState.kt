package com.example.androidschoolproject.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * the main Data class for countryState response
 * @param status status of the response
 * @param data CountryState data which is a list of States
 */
@Serializable
data class CountryStatesResponse(
    @SerialName("status")
    val status: String,
    @SerialName("data")
    val data: List<CountryState>,
)

/**
 * Data class for CountryState data
 * @param state name of the state
 */
@Serializable
data class CountryState(
    @SerialName("state")
    val state: String,
)
