package com.example.androidschoolproject.ui.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Temperature(temp: Float, size: ViewSize) {
    if (size == ViewSize.SMALL) {
        Text(text = "${temp.format(1)}°C")
    } else {
        Text(text = "Temperature: ${temp.format(1)}°C")
    }
}

@Composable
fun WindDirection(directionFloat: Float, size: ViewSize) {
    val direction = directionBasedOnDegrees(directionFloat)
    val text = if (size == ViewSize.SMALL) {
        Text(text = shortNotation(direction))
    } else {
        Text("Wind direction: $direction")
    }
}
fun Float.format(digits: Int) = "%.${digits}f".format(this)
fun directionBasedOnDegrees(direction: Float): String {
    return when {
        direction >= 11.25 && direction < 33.75 -> "North North East"
        direction >= 33.75 && direction < 56.25 -> "North East"
        direction >= 56.25 && direction < 78.75 -> "East North East"
        direction >= 78.75 && direction < 101.25 -> "East"
        direction >= 101.25 && direction < 123.75 -> "East South East"
        direction >= 123.75 && direction < 146.25 -> "South East"
        direction >= 146.25 && direction < 168.75 -> "South South East"
        direction >= 168.75 && direction < 191.25 -> "South"
        direction >= 191.25 && direction < 213.75 -> "South South West"
        direction >= 213.75 && direction < 236.25 -> "South West"
        direction >= 236.25 && direction < 258.75 -> "West South West"
        direction >= 258.75 && direction < 281.25 -> "West"
        direction >= 281.25 && direction < 303.75 -> "West North West"
        direction >= 303.75 && direction < 326.25 -> "North West"
        direction >= 326.25 && direction < 348.75 -> "North North West"
        else -> "North"
    }
}
fun shortNotation(direction: String): String {
    var short = ""
    for (letter in direction) {
        if (letter in 'A'..'Z') {
            short += letter
        }
    }
    return short
}
