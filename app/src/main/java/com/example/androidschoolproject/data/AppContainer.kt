package com.example.androidschoolproject.data

import android.content.Context

interface AppContainer {
    val weatherCityRepository: WeatherCityRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val weatherCityRepository: WeatherCityRepository by lazy {
        OfflineWeatherCityRepository(InventoryDatabase.getDatabase(context).weatherDao())
    }
}
