package com.example.androidschoolproject.location

class FakeLocationManager : LocationManager {
    override fun getCurrentLocation(callback: (Double, Double) -> Unit) {
        callback(0.0, 0.0)
    }
}
