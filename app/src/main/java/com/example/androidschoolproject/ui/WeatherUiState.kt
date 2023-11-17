package com.example.androidschoolproject.ui

// import com.example.androidschoolproject.data.LocalWeatherDataProvider
// import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.data.LocalWeatherDataProvider
import com.example.androidschoolproject.network.City
import com.example.androidschoolproject.network.Country
import com.example.androidschoolproject.network.CountryState
import com.example.androidschoolproject.network.WeatherCity

sealed interface WeatherUiState {
    data class MyState(
        val cityList: List<WeatherCity> = mutableListOf(),
        val currentCity: WeatherCity = LocalWeatherDataProvider.defaultWeather,
        // val localCity: WeatherCity = LocalWeatherDataProvider.defaultWeather,
        val localCity: WeatherCity? = null,
        val countries: List<Country>? = null,
        val states: List<CountryState>? = null,
        val cities: List<City>? = null,
        val isShowingHomepage: Boolean = true,
        val isShowingAddCityBox: Boolean = false,
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val key: String = "",
        val countryName: String = "Select Country",
        val stateName: String = "Select State",
        val cityName: String = "Select City",
    ) : WeatherUiState

    object Error : WeatherUiState
    object Loading : WeatherUiState
}
