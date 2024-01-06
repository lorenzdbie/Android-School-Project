package com.example.androidschoolproject.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices

/**
 * GpsLocationManager class to get current location of the device
 * @param context context of the application
 */
class GpsLocationManager(private val context: Context) : LocationManager {

    /**
     * get current location of the device
     * @param callback callback function to return the location as latitude and longitude
     */
    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(callback: (Double, Double) -> Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val lat = location.latitude
                    val long = location.longitude
                    callback(lat, long)
                }
            }
            .addOnFailureListener { exception ->
                // Handle location retrieval failure
                exception.printStackTrace()
            }
    }
}
