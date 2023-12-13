package com.example.androidschoolproject.ui.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Context.hasLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    ) == PackageManager.PERMISSION_GRANTED &&
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED
}

//@SuppressLint("MissingPermission")
//fun getCurrentLocation(context: Context, callback: (Double, Double) -> Unit) {
//    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
//    fusedLocationClient.lastLocation
//        .addOnSuccessListener { location ->
//            if (location != null) {
//                val lat = location.latitude
//                val long = location.longitude
//                callback(lat, long)
//            }
//        }
//        .addOnFailureListener { exception ->
//            // Handle location retrieval failure
//            exception.printStackTrace()
//        }
//}
//
//fun startUpdate(viewModel: WeatherViewModel, context: Context) {
//    getCurrentLocation(context) { lat, long ->
//        viewModel.updateLocation(longitude = long, latitude = lat)
//    }
//}
