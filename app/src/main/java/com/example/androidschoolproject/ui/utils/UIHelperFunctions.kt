package com.example.androidschoolproject.ui.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.androidschoolproject.R
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun Temperature(temp: Double, size: ViewSize) {
    if (size == ViewSize.SMALL) {
        Text(text = "${String.format("%.1f", temp)}°C")
    } else {
        Text(text = "Temperature: ${String.format("%.1f", temp)}°C")
    }
}

@Composable
fun WindDirection(direction: Int, size: ViewSize) {
    val direction = directionBasedOnDegrees(direction)
    val text = if (size == ViewSize.SMALL) {
        Text(text = shortNotation(direction))
    } else {
        Text("Wind direction: $direction")
    }
}
fun Float.format(digits: Int) = "%.${digits}f".format(this)
fun directionBasedOnDegrees(direction: Int): String {
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

fun getWeatherIcon(icon: String) : Int{
   return  when (icon) {
        "01d" -> R.drawable._1d
        "01n" -> R.drawable._1n
        "02d" -> R.drawable._2d
        "02n" -> R.drawable._2n
        "03d" -> R.drawable._3d
        "03n" -> R.drawable._3d
        "04d" -> R.drawable._4d
        "04n" -> R.drawable._4d
        "09d" -> R.drawable._9d
        "10d" -> R.drawable._10d
        "10n" -> R.drawable._10n
        "11d" -> R.drawable._11d
        "13d" -> R.drawable._13d
        "50d" -> R.drawable._50d
        else -> R.drawable._1d
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



fun formatToLocalDateTime(dateString: String): String? {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    val outputFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US)

    val date = inputFormat.parse(dateString)
    return date?.let { outputFormat.format(it) }
}


fun pollutionUnit(unit: String): String {
    return when (unit) {
        "p1" -> "ug/m3"  // pm10
        "p2" -> "ug/m3"  // pm2.5
        "o3" -> "ppb O3"  // Ozone O3
        "n2" -> "ppb NO2"  // Nitrogen dioxide NO2
        "s2" -> "ppb SO2"  // Sulfur dioxide SO2
        "co" -> "ppm CO"  // Carbon monoxide CO
        else -> ""
    }
}
