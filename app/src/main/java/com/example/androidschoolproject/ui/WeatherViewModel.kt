package com.example.androidschoolproject.ui

import androidx.lifecycle.ViewModel
import com.example.androidschoolproject.data.LocalWeatherDataProvider
import com.example.androidschoolproject.model.WeatherCity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class WeatherViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        WeatherUiState(
            cityList = LocalWeatherDataProvider.getWeatherCityData(),
            currentCity = LocalWeatherDataProvider.getWeatherCityData().getOrElse(0) {
                LocalWeatherDataProvider.defaultWeather
            },
        ),
    )

    val uiState: StateFlow<WeatherUiState> = _uiState

    fun updateLocation(longitude: Double, latitude: Double) {
        _uiState.update {
            it.copy(
                longitude = longitude,
                latitude = latitude,
            )
        }
    }

    fun updateDetailScreenStates(selectedCity: WeatherCity) {
        _uiState.update {
            it.copy(
                currentCity = selectedCity,
                isShowingHomepage = false,
            )
        }
    }

    fun updateAddCityScreenStates() {
        _uiState.update {
            it.copy(
                isShowingAddCityBox = true,
            )
        }
    }

    fun resetAddCityScreenStates() {
        _uiState.update {
            it.copy(
                isShowingAddCityBox = false,
            )
        }
    }

    fun resetHomeScreenStates() {
        _uiState.update {
            it.copy(
                currentCity = LocalWeatherDataProvider.getWeatherCityData().getOrElse(0) {
                    LocalWeatherDataProvider.defaultWeather
                },
                isShowingHomepage = true,
            )
        }
    }
}

data class WeatherUiState(
    val cityList: List<WeatherCity> = emptyList(),
    val currentCity: WeatherCity = LocalWeatherDataProvider.defaultWeather,
    val isShowingHomepage: Boolean = true,
    val isShowingAddCityBox: Boolean = false,
    val hasLocationPermission: Boolean = false,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)
