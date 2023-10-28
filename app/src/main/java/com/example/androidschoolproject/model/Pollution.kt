package com.example.androidschoolproject.model

data class Pollution(
    val timeStamp: String,
    val aqiUsa: Float,
    val mainUsa: String,
    val aqiChina: Float,
    val mainChina: String,
)
