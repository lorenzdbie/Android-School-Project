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

    fun updateCurrentWeather(selectedCity: WeatherCity) {
        _uiState.update {
            it.copy(currentCity = selectedCity)
        }
    }
}

data class WeatherUiState(
    val cityList: List<WeatherCity> = emptyList(),
    val currentCity: WeatherCity? = null,
)
