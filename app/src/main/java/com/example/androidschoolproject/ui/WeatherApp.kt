package com.example.androidschoolproject.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.ui.utils.WeatherContentType
import kotlinx.coroutines.launch

/**
 * the Main WeatherApp composable that creates the appropriate viewModel and the contentType depending on the screenSize
 * @param locationEnabled boolean whether or not the app will use location data
 * @param windowSize the size of the devices screen size
 */
// @RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherApp(
    locationEnabled: Boolean,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val viewModel: WeatherViewModel = if (locationEnabled) {
        viewModel(factory = AppViewModelProvider.Factory)
    } else {
        viewModel(factory = AppViewModelProvider.FactoryWithoutLocation)
    }
    val weatherUiState by viewModel.uiState.collectAsState()
    val apiUiState = viewModel.apiState
    val isLoading by viewModel.isLoading.collectAsState()

    val contentType: WeatherContentType
    val coroutineScope = rememberCoroutineScope()

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
        weatherUiState = weatherUiState,
        apiUiState = apiUiState,
        isLoading = isLoading,
        onCityCardPressed = { city: WeatherCity -> viewModel.updateDetailScreenStates(selectedCity = city) },
        onCityCardDelete = { city: WeatherCity -> viewModel.deleteCity(city) },
        onDetailScreenBackPressed = { viewModel.resetHomeScreenStates() },
        collectCountries = { viewModel.getCountries() },
        collectStates = { country: String -> viewModel.getStates(country = country) },
        collectCities = { country: String, state: String ->
            viewModel.getCities(
                country = country,
                state = state,
            )
        },
        onCitySelect = { city: String -> viewModel.addCityName(city = city) },
        onClickAddCity = { country: String, state: String, city: String ->
            coroutineScope.launch {
                viewModel.getCity(
                    country = country,
                    state = state,
                    city = city,
                )
                viewModel.resetAddCityScreenStates()
            }
        },
        onAddCityScreenPressed = { viewModel.updateAddCityScreenStates() },
        onAddCityClosedPressed = { viewModel.resetAddCityScreenStates() },
        onRefreshContent = { viewModel.refreshContent() },
        onDismissError = { viewModel.dismissError() },
        modifier = modifier,
    )
}
