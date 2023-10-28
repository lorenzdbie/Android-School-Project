package com.example.androidschoolproject.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.ui.utils.WeatherContentType
import com.example.androidschoolproject.ui.utils.WeatherNavigationType

@Composable
fun WeatherApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val viewModel: WeatherViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState().value
    val contentType: WeatherContentType
    val navigationType: WeatherNavigationType

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = WeatherNavigationType.BOTTOM_NAVIGATION
            contentType = WeatherContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = WeatherNavigationType.NAVIGATION_RAIL
            contentType = WeatherContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = WeatherNavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = WeatherContentType.LIST_AND_DETAIL
        }
        else -> {
            navigationType = WeatherNavigationType.BOTTOM_NAVIGATION
            contentType = WeatherContentType.LIST_ONLY
        }
    }

    WeatherHomeScreen(
        navigationType = navigationType,
        contentType = contentType,
        weatherUiState = uiState,
        onCityCardPressed = { city: WeatherCity ->
            viewModel.updateCurrentWeather(
                selectedCity = city,
            )
        },
        modifier = modifier,
    )
}
