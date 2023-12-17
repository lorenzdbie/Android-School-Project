package com.example.androidschoolproject.test

import com.example.androidschoolproject.data.OnlineApiRepository
import com.example.androidschoolproject.test.fake.FakeApiDataSource
import com.example.androidschoolproject.test.fake.FakeApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class OnlineApiRepositoryTest {

    @Test
    fun onlineApiRepository_getCountries_verifyCountryList() =
        runTest {
            val repository = OnlineApiRepository(weatherApiService = FakeApiService())
            assertEquals(FakeApiDataSource.countries, repository.getCountries())
        }

    @Test
    fun onlineApiRepository_getStates_verifyStatesList() =
        runTest {
            val repository = OnlineApiRepository(weatherApiService = FakeApiService())
            val country = "test"
            assertEquals(FakeApiDataSource.states, repository.getStates(country = country))
        }

    @Test
    fun onlineApiRepository_getCities_verifyCityList() =
        runTest {
            val repository = OnlineApiRepository(weatherApiService = FakeApiService())
            val country = "test"
            val state = "test"
            assertEquals(
                FakeApiDataSource.cities,
                repository.getCities(country = country, state = state),
            )
        }

    @Test
    fun onlineApiRepository_getCity_verifyWeatherCity() =
        runTest {
            val repository = OnlineApiRepository(weatherApiService = FakeApiService())
            val country = "test"
            val state = "test"
            val city = "test"

            val expectedWeatherCity = FakeApiDataSource.localCity.copy(creationTime = 0)
            val actualWeatherCity =
                repository.getCity(country = country, state = state, city = city)
                    .copy(creationTime = 0)

            assertEquals(
                expectedWeatherCity,
                actualWeatherCity,
            )
        }

    @Test
    fun onlineApiRepository_getNearestCity_verifyWeatherCity() =
        runTest {
            val repository = OnlineApiRepository(weatherApiService = FakeApiService())
            val longitude = 0.0
            val latitude = 0.0

            val expectedWeatherCity = FakeApiDataSource.localCity.copy(creationTime = 0)
            val actualWeatherCity =
                repository.getNearestCity(longitude = longitude, latitude = latitude)
                    .copy(creationTime = 0)

            assertEquals(
                expectedWeatherCity,
                actualWeatherCity,
            )
        }
}
