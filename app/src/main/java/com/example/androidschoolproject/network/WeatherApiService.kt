package com.example.androidschoolproject.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.airvisual.com/v2/"
private const val API_KEY = "efc93cd2-4e04-445e-beec-c8e9d2b5aca1"

private val json = Json {
    ignoreUnknownKeys = true
}

private val interceptor = Interceptor { chain ->
    val original: Request = chain.request()
    val originalHttpUrl = original.url

    val url = originalHttpUrl.newBuilder()
        .addQueryParameter("key", API_KEY)
        .build()

    val requestBuilder: Request.Builder = original.newBuilder()
        .url(url)

    val request: Request = requestBuilder.build()
    chain.proceed(request)
}

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .build()

private val retrofit = Retrofit.Builder().addConverterFactory(
    json.asConverterFactory("application/json".toMediaType()),
)
    .baseUrl(BASE_URL).client(okHttpClient)
    .build()

interface WeatherApiService {

    @GET("nearest_city")
    suspend fun getNearestCity(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
//        @Query("key") apiKey: String,
    ): WeatherCityResponse

    @GET("countries")
    suspend fun getCountries(/*@Query("key") apiKey: String*/): CountriesResponse

    @GET("states")
    suspend fun getStates(
        @Query("country") country: String,
//        @Query("key") apiKey: String,
    ): CountryStatesResponse

    @GET("cities")
    suspend fun getCities(
        @Query("state") state: String,
        @Query("country") country: String,
//        @Query("key") apiKey: String,
    ): CitiesResponse

    @GET("city")
    suspend fun getCity(
        @Query("city") city: String,
        @Query("state") state: String,
        @Query("country") country: String,
//        @Query("key") apiKey: String,
    ): WeatherCityResponse
}

object WeatherApi {
    val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}
