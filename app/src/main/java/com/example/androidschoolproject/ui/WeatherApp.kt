package com.example.androidschoolproject.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.ui.utils.WeatherContentType
import getCurrentLocation
import hasLocationPermission

@Composable
fun WeatherApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val viewModel: WeatherViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState().value
    val contentType: WeatherContentType
    val context = LocalContext.current

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

    if (hasLocationPermission(context)) {
        getCurrentLocation(context = context) { lat, long ->
            viewModel.updateLocation(longitude = long as Double, latitude = lat as Double)
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
        onAddCityPressed = { viewModel.updateAddCityScreenStates() },
        onAddCityClosedPressed = { viewModel.resetAddCityScreenStates() },
        modifier = modifier,
    )
}
