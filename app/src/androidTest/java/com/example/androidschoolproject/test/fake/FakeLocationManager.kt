package com.example.androidschoolproject.test.fake

import com.example.androidschoolproject.location.LocationManager

class FakeLocationManager : LocationManager {
    override fun getCurrentLocation(callback: (Double, Double) -> Unit) {
        callback(0.0, 0.0)
    }
}
