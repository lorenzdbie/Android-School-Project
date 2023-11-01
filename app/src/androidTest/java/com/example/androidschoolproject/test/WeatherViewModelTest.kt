package com.example.androidschoolproject.test

import com.example.androidschoolproject.data.LocalWeatherDataProvider
import com.example.androidschoolproject.ui.WeatherViewModel
import org.junit.Before
import org.junit.Test

class WeatherViewModelTest {
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setUp() {
        viewModel = WeatherViewModel()
    }

    @Test
    fun testupdateDetailScreenStates() {
        // Given
        val selectedCity = LocalWeatherDataProvider.getWeatherCityData().first()

        viewModel.updateDetailScreenStates(selectedCity)

        // Then
        assert(viewModel.uiState.value.currentCity == selectedCity)
        assert(!viewModel.uiState.value.isShowingHomepage)
    }

    @Test
    fun testUpdateAddCityScreenStates() {
        // When
        viewModel.updateAddCityScreenStates()

        // Then
        assert(viewModel.uiState.value.isShowingAddCityBox)
    }

    @Test
    fun `testResetAddCityScreenStates`() {
        // When
        viewModel.resetAddCityScreenStates()

        // Then
        assert(!viewModel.uiState.value.isShowingAddCityBox)
    }

    @Test
    fun testResetHomeScreenStates() {
        // When
        viewModel.resetHomeScreenStates()

        // Then
        assert(viewModel.uiState.value.isShowingHomepage)
    }
}
