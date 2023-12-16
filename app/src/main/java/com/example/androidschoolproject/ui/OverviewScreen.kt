package com.example.androidschoolproject.ui

import androidx.annotation.StringRes
import com.example.androidschoolproject.R

enum class OverviewScreen(@StringRes val title: Int) {
    Location(title = R.string.location_title),
    Start(title = R.string.weather_title)

}