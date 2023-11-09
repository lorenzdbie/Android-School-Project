package com.example.androidschoolproject.ui

import androidx.lifecycle.ViewModel // ktlint-disable import-ordering
import androidx.lifecycle.viewModelScope
import com.example.androidschoolproject.data.LocalWeatherDataProvider
// import com.example.androidschoolproject.data.LocalWeatherDataProvider
// import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.network.WeatherApi
import com.example.androidschoolproject.network.WeatherCity
import com.example.androidschoolproject.network.createWeatherCity
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

    fun getNearestCity(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                val localCityResponse = WeatherApi.retrofitService.getNearestCity(
                    longitude = longitude,
                    latitude = latitude,
                    apiKey = key,
                )
                val localCity = createWeatherCity(localCityResponse)
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
                val countryResponse = WeatherApi.retrofitService.getCountries(apiKey = key)
                _uiState.update {
                    it.copy(countries = countryResponse.data)
                }
            } catch (e: IOException) {
                e.stackTrace
            }
        }
    }

    fun getStates(country: String) {
        viewModelScope.launch {
            try {
                val stateResponse = WeatherApi.retrofitService.getStates(country = country, apiKey = key)
                _uiState.update {
                    it.copy(
                        countryName = country,
                        cityName = "Select City",
                        stateName = "Select State",
                        states = stateResponse.data,
                    )
                }
            } catch (e: IOException) {
                e.stackTrace
            }
        }
    }

    fun getCities(country: String, state: String) {
        viewModelScope.launch {
            try {
                val citiesResult = WeatherApi.retrofitService.getCities(
                    country = country,
                    state = state,
                    apiKey = key,
                )
                _uiState.update {
                    it.copy(
                        stateName = state,
                        cities = citiesResult.data,
                        cityName = "Select City",
                    )
                }
            } catch (e: IOException) {
                e.stackTrace
            }
        }
    }

    fun addCityName(city: String) {
        _uiState.update {
            it.copy(
                cityName = city,
            )
        }
    }

    fun getCity(country: String, state: String, city: String) {
        viewModelScope.launch {
            try {
                val cityResponse = WeatherApi.retrofitService.getCity(country = country, state = state, city = city, apiKey = key)
                val updatedCitylist: MutableList<WeatherCity> = _uiState.value.cityList
                updatedCitylist.add(createWeatherCity(cityResponse))
                _uiState.update {
                    it.copy(
                        cityList = updatedCitylist,
                        cityName = "Select City",
                        stateName = "Select State",
                        countryName = "Select Country",
                        countries = null,
                        states = null,
                        cities = null,
                    )
                }
            } catch (e: IOException) {
                e.stackTrace
            }
        }
    }
}
