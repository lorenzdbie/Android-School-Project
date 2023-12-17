package com.example.androidschoolproject.test

import com.example.androidschoolproject.location.FakeLocationManager
import com.example.androidschoolproject.test.fake.FakeApiRepository
import com.example.androidschoolproject.test.fake.FakeWeatherCityRepository
import com.example.androidschoolproject.ui.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class TestDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

class WeatherViewModelTest {
    private lateinit var viewModel: WeatherViewModel

    private val country = "countryNameTest"
    private val state = "stateNameTest"
    val city = "cityNameTest"

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = WeatherViewModel(
            weatherCityRepository = FakeWeatherCityRepository(),
            apiRepository = FakeApiRepository(),
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun after() {
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
    fun testResetAddCityScreenStates() {
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

    @Test
    fun testGetCountries() {
        assertNull(viewModel.uiState.value.countries)
        viewModel.getCountries()

        assertNotNull(viewModel.uiState.value.countries)
    }

    @Test
    fun testGetStates() {
        assertNull(viewModel.uiState.value.states)
        viewModel.getStates(country = country)

        assertNotNull(viewModel.uiState.value.states)
        assertEquals(country, viewModel.uiState.value.countryName)
    }

    @Test
    fun testGetCities() {
        assertNull(viewModel.uiState.value.cities)
        viewModel.getCities(country = country, state = state)

        assertNotNull(viewModel.uiState.value.cities)
        assertEquals(state, viewModel.uiState.value.stateName)
    }

    @Test
    fun testAddCityName() {
        viewModel.addCityName(city = city)
        assertEquals(city, viewModel.uiState.value.cityName)
    }

    @Test
    fun testDeleteCity() {
        val cityListSize = viewModel.uiState.value.cityList.size
        val city = viewModel.uiState.value.cityList[0]
        viewModel.deleteCity(city)
        assertEquals(
            cityListSize - 1,
            viewModel.uiState.value.cityList.size,
        )
    }

    @Test
    fun testGetCity() {
        val cityListSize = viewModel.uiState.value.cityList.size
        viewModel.getCity(country = country, state = state, city = city)
        assertEquals(
            cityListSize + 1,
            viewModel.uiState.value.cityList.size,
        )
    }

    @Test
    fun testGetNearestCity() {
        assertNull(viewModel.uiState.value.localCity)
        viewModel = WeatherViewModel(
            weatherCityRepository = FakeWeatherCityRepository(),
            apiRepository = FakeApiRepository(),
            locationManager = FakeLocationManager(),
        )
        assertNotNull(viewModel.uiState.value.localCity)
    }
}
