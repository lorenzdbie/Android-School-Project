package com.example.androidschoolproject.model

data class WeatherCity(
    val city: String,
    val state: String,
    val country: String,
    val location: Location,
    val weather: Weather,
    val pollution: Pollution,
)
