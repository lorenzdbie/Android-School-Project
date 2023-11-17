package com.example.androidschoolproject.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryStatesResponse(
    @SerialName("status")
    val status: String,
    @SerialName("data")
    val data: List<CountryState>,
)

@Serializable
data class CountryState(
    @SerialName("state")
    val state: String,
)
