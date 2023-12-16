package com.example.androidschoolproject.ui

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androidschoolproject.R
import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.ui.utils.ViewSize
import com.example.androidschoolproject.ui.utils.WindDirection
import com.example.androidschoolproject.ui.utils.formatToLocalDateTime
import com.example.androidschoolproject.ui.utils.pollutionUnit


@Composable
fun DetailsWeatherScreen(
    weatherUiState: WeatherUiState,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false
) {
    BackHandler {
        onBackPressed()
    }
    val city = weatherUiState.currentCity
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
        ) {
            if (isFullScreen) {
                IconButton(
                    onClick = onBackPressed,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(R.dimen.detail_topbar_back_button_padding_horizontal))
                        .background(MaterialTheme.colorScheme.surface, shape = CircleShape),
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.navigation_back),
                    )
                }
            }
            if (city != null) {
                DetailHeader(city = city)
                Divider()
                //  Spacer(modifier = Modifier.weight(1f))
                LazyColumn {
                    item { WeatherDetails(city = city) }
                    item { Divider() }
                    item { PollutionDetails(city = city) }
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "Last updated : ${formatToLocalDateTime(dateString = city.weather.timeStamp)}",
                    //  text= "Last update: ${city.weather.timeStamp}",
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    )
                )
            }
        }
    }
}


@Composable
fun DetailHeader(city: WeatherCity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text("Country: \n${city.country}")
        Text("State: \n${city.state}")
        Text("City: \n${city.city}")
    }
}

@Composable
fun WeatherDetails(city: WeatherCity) {
    Column {
        Text("Weather", modifier = Modifier.align(Alignment.CenterHorizontally))
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                DetailCard(detailTitle = R.string.temperature, value = city.weather.temperature, unit = "°C")
       //         Temperature2(city = city)
                Wind(city = city)
            }
            Column(modifier = Modifier.weight(1f)) {
                WeatherIconCard(city = city)
                DetailCard(detailTitle = R.string.humidity, value = city.weather.humidity, unit = "%")
                DetailCard(detailTitle = R.string.pressure, value = city.weather.atmosphericPressure, unit = " hPa")
       //         Humidity(city = city)
     //           Pressure(city = city)
            }
        }
    }
}


@Composable
fun DetailCard(@StringRes detailTitle: Int, value: Number, unit: String) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.card_padding))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.card_padding))
        ) {
            Text(text = stringResource(id = detailTitle))
            Text(
                "$value$unit",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}


//@Composable
//fun Temperature2(city: WeatherCity) {
//    Card(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(10.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(10.dp)
//        ) {
//            Text("Temperature")
//            Text(
//                "${city.weather.temperature}°C",
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )
//        }
//    }
//}

@Composable
fun Wind(city: WeatherCity) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.card_padding))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.card_padding))
        ) {
            Text(text = stringResource(id = R.string.wind))
            Text(text = stringResource(id = R.string.speed_text, city.weather.windSpeed))
            WindDirection(direction = city.weather.windDirection, size = ViewSize.LARGE)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                WindDirectionRose(city.weather.windDirection)
            }
        }
    }
}

//@Composable
//fun Humidity(city: WeatherCity) {
//    Card(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(10.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(10.dp)
//        ) {
//            Text("Humidity")
//            Text(
//                "${city.weather.humidity}%",
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )
//        }
//    }
//}
//
//@Composable
//fun Pressure(city: WeatherCity) {
//    Card(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(10.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(10.dp)
//        ) {
//            Text("Pressure")
//            Text(
//                "${city.weather.atmosphericPressure}hPa",
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )
//        }
//    }
//}

@Composable
fun WeatherIconCard(city: WeatherCity) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp), Arrangement.Center, Alignment.CenterHorizontally
        ) {
            WeatherIcon(icon = city.weather.weatherIcon, viewSize = ViewSize.LARGE)
        }
    }
}

@Composable
fun PollutionDetails(city: WeatherCity) {
    Column {
        Text("Air Quality", modifier = Modifier.align(Alignment.CenterHorizontally))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = Modifier.weight(1f), Arrangement.SpaceBetween) {
                DetailCard(detailTitle = R.string.us_standard, value = city.pollution.aqiUsa, unit = " ${pollutionUnit(city.pollution.mainUsa)}")
          //      UsaQualityCard(city = city)
            }
            Column(modifier = Modifier.weight(1f), Arrangement.SpaceBetween) {
          //      ChinaQualityCard(city = city)
                DetailCard(detailTitle = R.string.china_standard, value = city.pollution.aqiChina, unit = " ${pollutionUnit(city.pollution.mainChina)}")
            }

        }
    }
}
//
//@Composable
//fun UsaQualityCard(city: WeatherCity) {
//    Card(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(10.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(10.dp)
//        ) {
//            Text("USA standard")
//            Text(
//                "${city.pollution.aqiUsa} ${pollutionUnit(city.pollution.mainUsa)}",
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )
//        }
//    }
//}
//
//@Composable
//fun ChinaQualityCard(city: WeatherCity) {
//    Card(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(10.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(10.dp)
//        ) {
//            Text("China Standard")
//            Text(
//                "${city.pollution.aqiUsa} ${pollutionUnit(city.pollution.mainUsa)}",
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )
//        }
//    }
//}

@Composable
private fun WindDirectionRose(windDirection: Int) {
    Box(
        modifier = Modifier
            .padding(15.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.compass), // Replace R.drawable.ic_compass with your actual compass image resource
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .scale(1.2f) // Adjust the scale factor as needed
                .graphicsLayer(
                    rotationZ = windDirection - 32f,
                    transformOrigin = TransformOrigin(0.5f, 0.5f),
                )
                .align(Alignment.Center),
        )
    }
}

// @Preview(showBackground = true)
// @Composable
// fun CityWeatherDetailsPreview() {
//    AndroidSchoolProjectTheme {
//        DetailsWeatherScreen(weatherUiState = WeatherUiState.MyState(), onBackPressed = {}, isFullScreen = true)
//    }
// }
