package com.example.androidschoolproject.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices

class GpsLocationManager(private val context: Context) : LocationManager {

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