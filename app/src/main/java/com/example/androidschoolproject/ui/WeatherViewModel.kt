package com.example.androidschoolproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidschoolproject.data.LocalWeatherDataProvider
import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.network.WeatherApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException

class WeatherViewModel : ViewModel() {

    private var key: String = ""

    private val _uiState = MutableStateFlow(
        WeatherUiState(
            cityList = LocalWeatherDataProvider.getWeatherCityData(),
            currentCity = LocalWeatherDataProvider.getWeatherCityData().getOrElse(0) {
                LocalWeatherDataProvider.defaultWeather
            },
        ),
    )

    val uiState: StateFlow<WeatherUiState> = _uiState

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
    fun updateKey(key: String) {
        this.key = key
    }

    fun updateLocation(longitude: Double, latitude: Double) {
        _uiState.update {
            it.copy(
                longitude = longitude,
                latitude = latitude,

            )
        }
    }

    fun getNearestCity() {
        viewModelScope.launch {
            try {
                val localCity = WeatherApi.retrofitService.getNearestCity(
                    longitude = uiState.value.longitude.toString(),
                    latitude = uiState.value.latitude.toString(),
                    apiKey = key,
                )
                _uiState.update {
                    it.copy(
                        localCity = localCity,
                    )
                }
            } catch (e: IOException) {
                e.localizedMessage
            }
        }
    }

    fun getCountries() {
        viewModelScope.launch {
            try {
                val countryResult = WeatherApi.retrofitService.getCountries(apiKey = key)
                _uiState.update {
                    it.copy(countries = countryResult)
                }
            } catch (e: IOException) {
                e.stackTrace
            }
        }
    }

    fun getStates(country: String) {
        viewModelScope.launch {
            val stateResult = WeatherApi.retrofitService.getStates(country = country, apiKey = key)
        }
    }

    fun getCities(country: String, state: String) {
        viewModelScope.launch {
            val citiesResult = WeatherApi.retrofitService.getCities(country = country, state = state, apiKey = key)
        }
    }

    fun getCity(country: String, state: String, city: String) {
        viewModelScope.launch {
            val cityResult = WeatherApi.retrofitService.getCity(country = country, state = state, city = city, apiKey = key)
        }
    }
}
