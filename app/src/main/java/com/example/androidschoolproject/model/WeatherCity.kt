// package com.example.androidschoolproject.model
//
// import kotlinx.serialization.SerialName
// import kotlinx.serialization.Serializable
//
// @Serializable
// data class WeatherCity(
//    @JvmField val id: String = generateUniqueId(),
//
//    @SerialName(value = "city")
//    val city: String,
//    val state: String,
//    val country: String,
//    val cityLocation: CityLocation,
//    val weather: Weather,
//    val pollution: Pollution,
// ) {
//    companion object {
//        private var idCounter = 0
//
//        private fun generateUniqueId(): String {
//            idCounter++
//            return "ID$idCounter"
//        }
//    }
// }
//
// @Serializable
// data class CityLocation(
//    val longitude: Double,
//    val latitude: Double,
// )
//
// @Serializable
// data class Weather(
//    val timeStamp: String,
//    val temperature: Float,
//    val atmosphericPressure: Float,
//    val humidity: Float,
//    val windSpeed: Float,
//    val windDirection: Float,
//    val weatherIcon: String,
// )
//
// @Serializable
// data class Pollution(
//    val timeStamp: String,
//    val aqiUsa: Float,
//    val mainUsa: String,
//    val aqiChina: Float,
//    val mainChina: String,
// )
