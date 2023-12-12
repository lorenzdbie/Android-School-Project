package com.example.androidschoolproject.location

interface LocationManager {

    fun getCurrentLocation(callback: (Double, Double) -> Unit)
}