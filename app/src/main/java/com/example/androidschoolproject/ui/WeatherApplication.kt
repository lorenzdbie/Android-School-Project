package com.example.androidschoolproject.ui

import android.app.Application
import com.example.androidschoolproject.data.AppContainer
import com.example.androidschoolproject.data.AppDataContainer

class WeatherApplication : Application() {
    lateinit var  container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)

    }
}