package com.example.androidschoolproject.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidschoolproject.data.ApiRepository
import com.example.androidschoolproject.data.WeatherCityRepository
import com.example.androidschoolproject.location.LocationManager
import com.example.androidschoolproject.network.WeatherCity
import com.example.androidschoolproject.network.createWeatherCity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val locationManager: LocationManager? = null
) : ViewModel() {


    private val _uiState = MutableStateFlow(WeatherUiState())

    init {
        if(locationManager != null) {
            getNearestCityBasedOnCurrentLocation()
        }
        viewModelScope.launch {
            weatherCityRepository.getAllWeatherCitiesStream().collect { weatherCityList ->
                _uiState.update {
                    if (weatherCityList.isNotEmpty()) {
                        it.copy(
                            cityList = weatherCityList,
                            currentCity = weatherCityList[0]
                        )
                    } else {
                        it.copy(
                            cityList = weatherCityList
                        )
                    }
                }
            }
        }
    }


    val uiState: StateFlow<WeatherUiState> = _uiState
    var apiState: ApiUiState by mutableStateOf(ApiUiState.Success)


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


    /**
     * gets the nearest WeatherCity based on coordinates
     * @param longitude longitude coordinate
     * @param latitude latitude coordinate
     */
    private fun getNearestCity(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            apiState = ApiUiState.Loading
            apiState = try {
                val localCityData = apiRepository.getNearestCity(longitude = longitude, latitude = latitude)
                val localCity = createWeatherCity(localCityData)
                _uiState.update {
                    it.copy(
                        localCity = localCity,
                        currentCity = localCity
                    )
                }
                ApiUiState.Success
            } catch (e: Exception) {
                ApiUiState.Error
            }
        }
    }

    /**
     * gets the nearest city based on current location
     */
    private fun getNearestCityBasedOnCurrentLocation() {
        locationManager?.getCurrentLocation { lat, long ->
            getNearestCity(latitude = lat, longitude = long)
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
                val states = apiRepository.getState(country = country)
                _uiState.update {
                    it.copy(
                        countryName = country,
                        cityName = "Select City",
                        stateName = "Select State",
                        states = states,
                    )
                }
                ApiUiState.Success
            } catch (e: Exception) {
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
                _uiState.update {
                    it.copy(
                        stateName = state,
                        cities = cities,
                        cityName = "Select City",
                    )
                }
                ApiUiState.Success
            } catch (e: Exception) {
                ApiUiState.Error
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
                val newWeatherCity = apiRepository.getCity(country = country, state = state, city = city)
                weatherCityRepository.insertWeatherCity(createWeatherCity(newWeatherCity))
                ApiUiState.Success
            } catch (e: Exception) {
                ApiUiState.Error
            }
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
