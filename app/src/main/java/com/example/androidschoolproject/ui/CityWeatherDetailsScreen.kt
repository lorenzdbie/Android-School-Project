package com.example.androidschoolproject.ui

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidschoolproject.R
import com.example.androidschoolproject.network.WeatherCity
import com.example.androidschoolproject.ui.theme.AndroidSchoolProjectTheme
import com.example.androidschoolproject.ui.utils.Temperature
import com.example.androidschoolproject.ui.utils.ViewSize
import com.example.androidschoolproject.ui.utils.WindDirection

@Composable
fun DetailsWeatherScreen(weatherUiState: WeatherUiState, onBackPressed: () -> Unit, modifier: Modifier = Modifier, isFullScreen: Boolean = false) {
    BackHandler {
        onBackPressed()
    }
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
            DetailHeader(city = weatherUiState.currentCity)
            //  Spacer(modifier = Modifier.weight(1f))
            WeatherDetails(city = weatherUiState.currentCity)
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
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Temperature(temp = city.weather.temperature, size = ViewSize.LARGE)
            Spacer(modifier = Modifier.width(16.dp))
            WindDirection(direction = city.weather.windDirection, size = ViewSize.LARGE)
            Spacer(modifier = Modifier.width(16.dp))
            WindDirectionRose(city.weather.windDirection)
        }
        WeatherIcon(icon = city.weather.weatherIcon, viewSize = ViewSize.LARGE)
    }
}

@Composable
fun WeatherInfoColumn(prefix: String, info: Float, size: ViewSize) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = when (size) {
                ViewSize.SMALL -> "$info"
                ViewSize.LARGE -> "${prefix}$info"
            },
            fontWeight = FontWeight.Bold,
        )
    }
}

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

@Preview(showBackground = true)
@Composable
fun CityWeatherDetailsPreview() {
    AndroidSchoolProjectTheme {
        DetailsWeatherScreen(weatherUiState = WeatherUiState(), onBackPressed = {}, isFullScreen = true)
    }
}
