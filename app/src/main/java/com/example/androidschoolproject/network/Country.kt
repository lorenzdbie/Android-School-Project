package com.example.androidschoolproject.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountriesResponse(
    @SerialName("status")
    val status: String,
    @SerialName("data")
    val data: List<Country>,
)

@Serializable
data class Country(
    @SerialName("country")
    val country: String,
)
