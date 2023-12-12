package com.example.androidschoolproject.ui

// import com.example.androidschoolproject.data.LocalWeatherDataProvider
// import com.example.androidschoolproject.model.WeatherCity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidschoolproject.data.WeatherCityRepository
import com.example.androidschoolproject.network.WeatherApi
import com.example.androidschoolproject.network.WeatherCity
import com.example.androidschoolproject.network.createWeatherCity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class WeatherViewModel(private val weatherCityRepository: WeatherCityRepository) : ViewModel() {

    private var key: String = ""
    private  val _uiState = MutableStateFlow(WeatherUiState())

    init {
  //      getSavedCities()
        viewModelScope.launch {
            weatherCityRepository.getAllWeatherCitiesStream().collect{ weatherCityList ->
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

   // var uiState: WeatherUiState by mutableStateOf(WeatherUiState.Loading)

//    fun getSavedCities(){
//        viewModelScope.launch {
//            weatherCityRepository.getAllWeatherCitiesStream().collect{ weatherCityList ->
//                uiState = if (weatherCityList.isNotEmpty()) {
//                    WeatherUiState.MyState(
//                        cityList = weatherCityList,
//                        currentCity = weatherCityList[0]
//                    )
//                } else {
//                    WeatherUiState.MyState(
//                        cityList = weatherCityList
//                    )
//                }
//                }
//            }
//        }

    fun updateDetailScreenStates(selectedCity: WeatherCity) {
        _uiState.update {
            it.copy(
                currentCity = selectedCity,
                isShowingHomepage = false,
            )
        }
    }

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
            apiState = ApiUiState.Loading
            apiState = try {
                val localCityResponse = WeatherApi.retrofitService.getNearestCity(
                    longitude = longitude,
                    latitude = latitude,
                    apiKey = key,
                )
                val localCity = createWeatherCity(localCityResponse)
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

    fun getCountries() {
        viewModelScope.launch {
            apiState = ApiUiState.Loading
            apiState = try {
                val countryResponse = WeatherApi.retrofitService.getCountries(apiKey = key)
                _uiState.update {
                    it.copy(countries = countryResponse.data)
                }
                ApiUiState.Success
            } catch (e: IOException) {
                ApiUiState.Error
            } catch (e: HttpException) {
                ApiUiState.Error
            }
        }
    }

    fun getStates(country: String) {
        viewModelScope.launch {
            apiState = ApiUiState.Loading
            apiState = try {
                val stateResponse = WeatherApi.retrofitService.getStates(country = country, apiKey = key)
                _uiState.update {
                    it.copy(
                        countryName = country,
                        cityName = "Select City",
                        stateName = "Select State",
                        states = stateResponse.data,
                    )
                }
                ApiUiState.Success
            } catch (e: Exception) {
                ApiUiState.Error
            }
        }
    }

    fun getCities(country: String, state: String) {
        viewModelScope.launch {
            apiState = ApiUiState.Loading
            apiState = try {
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

    fun getCity(country: String, state: String, city: String) {
        viewModelScope.launch {
            apiState = ApiUiState.Loading
            apiState = try {
                val cityResponse = WeatherApi.retrofitService.getCity(country = country, state = state, city = city, apiKey = key)
                val newWeatherCity = createWeatherCity(cityResponse)
                weatherCityRepository.insertWeatherCity(newWeatherCity)
                _uiState.update {
                    it.copy(
                        cityName = "Select City",
                        stateName = "Select State",
                        countryName = "Select Country",
                        countries = null,
                        states = null,
                        cities = null,
                    )
                }
                ApiUiState.Success
            } catch (e: Exception) {
                ApiUiState.Error
            }
        }
    }
}
