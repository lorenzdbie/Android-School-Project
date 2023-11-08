package com.example.androidschoolproject.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CitiesResponse(
    @SerialName("status")
    val status: String,
    @SerialName("data")
    val data: List<City>,
)

@Serializable
data class City(
    @SerialName("city")
    val city: String,
)
