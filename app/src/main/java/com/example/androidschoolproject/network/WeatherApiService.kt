package com.example.androidschoolproject.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.airvisual.com/v2/"

private val retrofit = Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {

    @GET("nearest_city")
    suspend fun getNearestCity(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("key") apiKey: String,
    ): String

    @GET("countries")
    suspend fun getCountries(@Query("key") apiKey: String): String

    @GET("states")
    suspend fun getStates(
        @Query("country") country: String,
        @Query("key") apiKey: String,
    ): String

    @GET("cities")
    suspend fun getCities(
        @Query("state") state: String,
        @Query("country") country: String,
        @Query("key") apiKey: String,
    ): String

    @GET("city")
    suspend fun getCity(
        @Query("city") city: String,
        @Query("state") state: String,
        @Query("country") country: String,
        @Query("key") apiKey: String,
    ): String
}

object WeatherApi {
    val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}
