package com.example.androidschoolproject.location

/**
 * Interface for LocationManager
 */
interface LocationManager {
    /**
     * get current location of the device
     * @param callback callback function to return the location as latitude and longitude
     */
    fun getCurrentLocation(callback: (Double, Double) -> Unit)
}
