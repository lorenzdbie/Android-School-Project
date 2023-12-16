package com.example.androidschoolproject.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.airvisual.com/v2/"

private val json = Json {
    ignoreUnknownKeys = true
}

private val retrofit = Retrofit.Builder().addConverterFactory(
//    Json {
//        ignoreUnknownKeys = true
//    }
    json.asConverterFactory("application/json".toMediaType()),
)
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {

    @GET("nearest_city")
    suspend fun getNearestCity(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("key") apiKey: String,
    ): WeatherCityResponse

    @GET("countries")
    suspend fun getCountries(@Query("key") apiKey: String): CountriesResponse

    @GET("states")
    suspend fun getStates(
        @Query("country") country: String,
        @Query("key") apiKey: String,
    ): CountryStatesResponse

    @GET("cities")
    suspend fun getCities(
        @Query("state") state: String,
        @Query("country") country: String,
        @Query("key") apiKey: String,
    ): CitiesResponse

    @GET("city")
    suspend fun getCity(
        @Query("city") city: String,
        @Query("state") state: String,
        @Query("country") country: String,
        @Query("key") apiKey: String,
    ): WeatherCityResponse
}

object WeatherApi {
    val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}
