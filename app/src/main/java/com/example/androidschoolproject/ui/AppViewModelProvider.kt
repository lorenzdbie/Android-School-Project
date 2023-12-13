package com.example.androidschoolproject.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            WeatherViewModel(
                weatherApplication().container.weatherCityRepository,
                weatherApplication().container.apiRepository,
                weatherApplication().container.locationManager
            )
        }
    }

    val FactoryWithoutLocation = viewModelFactory {
        initializer {
            WeatherViewModel(
                weatherApplication().container.weatherCityRepository,
                weatherApplication().container.apiRepository,
            )
        }
    }
}

fun CreationExtras.weatherApplication(): WeatherApplication =
    (this[APPLICATION_KEY] as WeatherApplication)