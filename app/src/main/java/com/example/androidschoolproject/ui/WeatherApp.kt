package com.example.androidschoolproject.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.ui.utils.WeatherContentType

@Composable
fun WeatherApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val viewModel: WeatherViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState().value
    val contentType: WeatherContentType

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
        onDetailScreenBackPressed = {
            viewModel.resetHomeScreenStates()
        },
        modifier = modifier,
    )
}
