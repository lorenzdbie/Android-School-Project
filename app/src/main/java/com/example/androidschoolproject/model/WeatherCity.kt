package com.example.androidschoolproject.model

data class WeatherCity(
    @JvmField val id: String = generateUniqueId(),
    val city: String,
    val state: String,
    val country: String,
    val location: Location,
    val weather: Weather,
    val pollution: Pollution,
) {
    companion object {
        private var idCounter = 0

        private fun generateUniqueId(): String {
            idCounter++
            return "ID$idCounter"
        }
    }
}
