package com.example.androidschoolproject.model

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(interval: Long): Flow<Location>

    fun getCurrentLocation(): Location

    class LocationException(message: String) : Exception()
}
