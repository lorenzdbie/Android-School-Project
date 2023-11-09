package com.example.androidschoolproject.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidschoolproject.model.getCurrentLocation
import com.example.androidschoolproject.model.startUpdate
import com.example.androidschoolproject.network.WeatherCity
import com.example.androidschoolproject.ui.utils.WeatherContentType

@Composable
fun WeatherApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val viewModel: WeatherViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState().value
    val contentType: WeatherContentType
    val context = LocalContext.current

    LaunchedEffect(true) {
        startUpdate(viewModel, context)
    }

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            contentType = WeatherContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            contentType = WeatherContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded -> {
            contentType = WeatherContentType.LIST_AND_DETAIL
        }
        else -> {
            contentType = WeatherContentType.LIST_ONLY
        }
    }

    WeatherHomeScreen(
        contentType = contentType,
        weatherUiState = uiState,
        onCityCardPressed = { city: WeatherCity ->
            viewModel.updateDetailScreenStates(
                selectedCity = city,
            )
        },
        onDetailScreenBackPressed = { viewModel.resetHomeScreenStates() },
        collectLocalCity = { getCurrentLocation(context) { lat, long -> viewModel.getNearestCity(latitude = lat, longitude = long) } },
        collectCountries = { viewModel.getCountries() },
        collectStates = { country: String -> viewModel.getStates(country = country) },
        collectCities = { country: String, state: String -> viewModel.getCities(country = country, state = state) },
        onCitySelect = { city: String -> viewModel.addCityName(city = city) },
        onClickAddCity = { country: String, state: String, city: String -> viewModel.getCity(country = country, state = state, city = city) },
        onAddCityScreenPressed = { viewModel.updateAddCityScreenStates() },
        onAddCityClosedPressed = { viewModel.resetAddCityScreenStates() },
        modifier = modifier,
    )
}
