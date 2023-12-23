package com.example.androidschoolproject.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidschoolproject.data.ApiRepository
import com.example.androidschoolproject.data.WeatherCityRepository
import com.example.androidschoolproject.location.LocationManager
import com.example.androidschoolproject.model.WeatherCity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * @constructor
 * @param weatherCityRepository the repository to the local database
 * @param apiRepository the repository to make api calls
 * @param locationManager a location manager to get the current location
 */

class WeatherViewModel(
    private val weatherCityRepository: WeatherCityRepository,
    private val apiRepository: ApiRepository,
    private val locationManager: LocationManager? = null,
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())

    val uiState: StateFlow<WeatherUiState> = _uiState
    var apiState: ApiUiState by mutableStateOf(ApiUiState.Success)
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    /**
     * initializes the local weather and/or the saved city instants from DB into state
     */
    init {
        if (locationManager != null) {
            getNearestCityBasedOnCurrentLocation()
        }
        viewModelScope.launch {
            weatherCityRepository.getAllWeatherCitiesStream().collect { weatherCityList ->
                _uiState.update {
                    it.copy(
                        cityList = weatherCityList,
                    )
                }
            }
        }
    }

    /**
     * updates the screenState to show details of selected city
     * @param selectedCity the chosen city to be viewed in detail
     */
    fun updateDetailScreenStates(selectedCity: WeatherCity) {
        _uiState.update {
            it.copy(
                currentCity = selectedCity,
                isShowingHomepage = false,
            )
        }
    }

    /**
     * deleted a city from the local repository
     * @param city the chosen city do be deleted
     */
    fun deleteCity(city: WeatherCity) {
        viewModelScope.launch {
            apiState = ApiUiState.Loading
            apiState = try {
                weatherCityRepository.deleteWeatherCity(city)
                ApiUiState.Success
            } catch (e: IOException) {
                ApiUiState.Error
            } catch (e: HttpException) {
                ApiUiState.Error
            } catch (e: Exception) {
                ApiUiState.Error
            }
        }
    }

    /**
     * updates the screenstate to show AddCityScreen
     */
    fun updateAddCityScreenStates() {
        _uiState.update {
            it.copy(
                isShowingAddCityBox = true,
            )
        }
    }

    /**
     * resets the screenState of AddCityScreen
     */
    fun resetAddCityScreenStates() {
        _uiState.update {
            it.copy(
                isShowingAddCityBox = false,
                cityName = "Select City",
                stateName = "Select State",
                countryName = "Select Country",
                countries = null,
                states = null,
                cities = null,
            )
        }
    }

    /**
     * resets the screenstate to show WeatherHomeScreen
     */
    fun resetHomeScreenStates() {
        _uiState.update {
            it.copy(
                isShowingHomepage = true,
            )
        }
    }

//    private fun addCall() {
//        _uiState.update { it.copy(nrOfCalls = it.nrOfCalls + 1) }
//    }

    /**
     * gets the nearest WeatherCity based on coordinates
     * @param longitude longitude coordinate
     * @param latitude latitude coordinate
     */

    private fun getNearestCity(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            apiState = ApiUiState.Loading
            apiState = try {
                val oldLocalCity = _uiState.value.localCity
                val newLocalCity =
                    apiRepository.getNearestCity(longitude = longitude, latitude = latitude)
                //   addCall()
                val localCity = oldLocalCity?.copy(
                    country = newLocalCity.country,
                    state = newLocalCity.state,
                    city = newLocalCity.city,
                    cityLocation = newLocalCity.cityLocation,
                    weather = newLocalCity.weather,
                    pollution = newLocalCity.pollution,
                )
                    ?: newLocalCity
                _uiState.update {
                    it.copy(
                        localCity = localCity,
                    )
                }
                ApiUiState.Success
            } catch (e: IOException) {
                ApiUiState.Error
            } catch (e: HttpException) {
                ApiUiState.Error
            } catch (e: Exception) {
                ApiUiState.Error
            }
        }
    }

    /**
     * gets the nearest city based on current location
     */

    private fun getNearestCityBasedOnCurrentLocation() {
        viewModelScope.launch {
            locationManager?.getCurrentLocation { lat, long ->
                getNearestCity(latitude = lat, longitude = long)
            }
        }
    }

    /**
     * fetches a list of countries and updates the state
     */
    fun getCountries() {
        viewModelScope.launch {
            apiState = ApiUiState.Loading
            apiState = try {
                val countries = apiRepository.getCountries()
                //        addCall()
                _uiState.update {
                    it.copy(countries = countries)
                }
                ApiUiState.Success
            } catch (e: IOException) {
                ApiUiState.Error
            } catch (e: HttpException) {
                ApiUiState.Error
            }
        }
    }

    /**
     * fetches a list of states based on parameters and updates the state
     * @param country a country name
     */
    fun getStates(country: String) {
        viewModelScope.launch {
            apiState = ApiUiState.Loading
            apiState = try {
                val states = apiRepository.getStates(country = country)
                //    addCall()
                _uiState.update {
                    it.copy(
                        countryName = country,
                        cityName = "Select City",
                        stateName = "Select State",
                        states = states,
                    )
                }
                ApiUiState.Success
            } catch (e: IOException) {
                ApiUiState.Error
            } catch (e: HttpException) {
                ApiUiState.Error
            }
        }
    }

    /**
     * fetches a list of cities based on parameters and updates the state
     * @param country a country name
     * @param state a state name
     */
    fun getCities(country: String, state: String) {
        viewModelScope.launch {
            apiState = ApiUiState.Loading
            apiState = try {
                val cities = apiRepository.getCities(country = country, state = state)
                //   addCall()
                _uiState.update {
                    it.copy(
                        stateName = state,
                        cities = cities,
                        cityName = "Select City",
                    )
                }
                ApiUiState.Success
            } catch (e: IOException) {
                ApiUiState.Error
            } catch (e: HttpException) {
                ApiUiState.Error
            }
        }
    }

    /**
     * saves the chosen city into the ui state
     */
    fun addCityName(city: String) {
        _uiState.update {
            it.copy(
                cityName = city,
            )
        }
    }

    /**
     * fetches city data based on parameters ans saves it to local repository
     * @param country a country name
     * @param state a state name
     * @param city a city name
     */

    fun getCity(country: String, state: String, city: String) {
        viewModelScope.launch {
            apiState = ApiUiState.Loading
            apiState = try {
                val newWeatherCity =
                    apiRepository.getCity(country = country, state = state, city = city)
                //  addCall()
                //   _uiState.update { it.copy(tempCity = newWeatherCity) }
                weatherCityRepository.insertWeatherCity(newWeatherCity)
                ApiUiState.Success
            } catch (e: IOException) {
                ApiUiState.Error
            } catch (e: HttpException) {
                ApiUiState.Error
            } catch (e: Exception) {
                ApiUiState.Error
                //        }
            }
        }
    }

    /**
     * refreshes the the users local weather city and the saved list of cities
     */
    fun refreshContent() {
        viewModelScope.launch {
            _isLoading.value = true
            if (locationManager != null) {
                getNearestCityBasedOnCurrentLocation()
                //    _uiState.update { it.copy(numberOfUpdates = 1) }
            }
            refreshWeatherCities()
            _isLoading.value = false
        }
    }

    /**
     * refreshes the saved list of weather cities
     */
    private suspend fun refreshWeatherCities() {
        apiState = ApiUiState.Loading
        apiState = try {
            for (city in _uiState.value.cityList) {
                delay(3000)
                val newCity = apiRepository.getCity(
                    country = city.country,
                    state = city.state,
                    city = city.city,
                )
                //   addCall()
                val updatedCity = city.copy(
                    weather = newCity.weather,
                    pollution = newCity.pollution,
                )
                weatherCityRepository.updateWeatherCity(updatedCity)
                //    _uiState.update { it.copy(numberOfUpdates = it.numberOfUpdates + 1) }
            }
            ApiUiState.Success
        } catch (e: IOException) {
            ApiUiState.Error
        } catch (e: HttpException) {
            ApiUiState.Error
        }
    }

    /**
     * reverts the ApiUiState from Error to Success
     */
    fun dismissError() {
        apiState = ApiUiState.Success
        resetAddCityScreenStates()
    }
}
