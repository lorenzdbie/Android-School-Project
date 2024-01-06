package com.example.androidschoolproject.data

import android.content.Context
import com.example.androidschoolproject.location.GpsLocationManager
import com.example.androidschoolproject.location.LocationManager
import com.example.androidschoolproject.network.WeatherApi

/**
 * Interface for AppContainer
 * This interface is used to provide dependencies to the classes
 */
interface AppContainer {
    val weatherCityRepository: WeatherCityRepository
    val apiRepository: ApiRepository
    val locationManager: LocationManager
}

/**
 * AppDataContainer class to provide dependencies to the classes
 * @param context context of the application
 */
class AppDataContainer(private val context: Context) : AppContainer {

    override val weatherCityRepository: WeatherCityRepository by lazy {
        OfflineWeatherCityRepository(InventoryDatabase.getDatabase(context).weatherDao())
    }
    override val apiRepository: ApiRepository by lazy {
        OnlineApiRepository(WeatherApi.retrofitService)
    }

    override val locationManager: LocationManager by lazy {
        GpsLocationManager(context)
    }
}
