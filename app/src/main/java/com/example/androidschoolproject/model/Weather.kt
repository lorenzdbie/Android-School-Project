package com.example.androidschoolproject.model

import androidx.annotation.DrawableRes

data class Weather(
    val timeStamp: String,
    val temperature: Float,
    val atmosphericPressure: Float,
    val humidity: Float,
    val windSpeed: Float,
    val windDirection: Float,
    val weatherIcon: String,
)
