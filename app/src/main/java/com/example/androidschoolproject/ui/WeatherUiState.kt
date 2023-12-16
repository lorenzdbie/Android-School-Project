package com.example.androidschoolproject.ui

import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.network.City
import com.example.androidschoolproject.network.Country
import com.example.androidschoolproject.network.CountryState

data class WeatherUiState(
    val cityList: List<WeatherCity> = mutableListOf(),
    val currentCity: WeatherCity? = null,
    val localCity: WeatherCity? = null,
    val countries: List<Country>? = null,
    val states: List<CountryState>? = null,
    val cities: List<City>? = null,
    val isShowingHomepage: Boolean = true,
    val isShowingAddCityBox: Boolean = false,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val countryName: String = "Select Country",
    val stateName: String = "Select State",
    val cityName: String = "Select City",

    val numberOfUpdates: Int = 0,
    val nrOfDbEntries: Int = 0,
    val tempCity: WeatherCity? = null,
    val nrOfCalls: Int = 0,
)

sealed interface ApiUiState {
    object Success : ApiUiState
    object Error : ApiUiState
    object Loading : ApiUiState
}
