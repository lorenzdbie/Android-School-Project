package com.example.androidschoolproject.test

import com.example.androidschoolproject.data.FakeWeatherCityRepository
import com.example.androidschoolproject.ui.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class TestDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()): TestWatcher() {
        override fun starting(description: Description) {
            Dispatchers.setMain(testDispatcher)
        }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
    }


class WeatherViewModelTest {
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = WeatherViewModel(weatherCityRepository = FakeWeatherCityRepository())
    }

    @After
    fun after(){
        Dispatchers.resetMain()
    }

    @Test
    fun testUpdateDetailScreenStates() {
        // Given
        val selectedCity = viewModel.uiState.value.cityList[0]

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
