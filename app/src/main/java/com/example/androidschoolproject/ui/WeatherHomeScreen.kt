package com.example.androidschoolproject.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidschoolproject.R
import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.ui.utils.ViewSize
import com.example.androidschoolproject.ui.utils.WeatherContentType
import com.example.androidschoolproject.ui.utils.WeatherNavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHomeScreen(
    navigationType: WeatherNavigationType,
    contentType: WeatherContentType,
    weatherUiState: WeatherUiState,
    onCityCardPressed: (WeatherCity) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            WeatherTopAppBar()
        },
        bottomBar = {
            if (contentType == WeatherContentType.LIST_AND_DETAIL) {
                WeatherExtendedBottomAddBar(onClick = { /*TODO*/ })
            } else {
                WeatherBottomAddBar(onClick = { /*TODO*/ })
            }
        },
    ) { it ->
        LazyColumn(contentPadding = it) {
            items(weatherUiState.cityList) {
                CityWeatherCard(
                    city = it,
                    modifier = Modifier.padding(5.dp),
                )
            }
        }
    }
}

@Composable
fun WeatherIcon(icon: String, viewSize: ViewSize, modifier: Modifier = Modifier) {
    @DrawableRes val weatherIcon = when (icon) {
        "01d" -> R.drawable._1d
        "01n" -> R.drawable._1n
        "02d" -> R.drawable._2d
        "02n" -> R.drawable._2n
        "03d" -> R.drawable._3d
        "04d" -> R.drawable._4d
        "09d" -> R.drawable._9d
        "010d" -> R.drawable._10d
        "10n" -> R.drawable._10n
        "11d" -> R.drawable._11d
        "13d" -> R.drawable._13d
        "50d" -> R.drawable._50d
        else -> R.drawable._1d
    }
    Box(
        modifier = Modifier.size(
            when (viewSize) {
                ViewSize.SMALL -> 50.dp
                ViewSize.LARGE -> 120.dp
                else -> 50.dp
            },
        ),
    ) {
        Image(
            painter = painterResource(id = weatherIcon),
            contentDescription = null,
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.FillWidth,

        )
    }
}

@Composable
private fun CityWeatherCard(city: WeatherCity, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
    ) {
        Row(modifier = modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
            Text(text = city.city, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.fillMaxWidth(0.7f))
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Text(text = "${city.weather.temperature}°C")
                Text(text = "${shortNotation(directionBasedOnDegrees(city.weather.windDirection))}")
            }
            WeatherIcon(icon = city.weather.weatherIcon, viewSize = ViewSize.SMALL)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                        .padding(
                            dimensionResource(id = R.dimen.padding_small),
                        ),
                    painter = painterResource(id = R.drawable._2d),
                    contentDescription = null,
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displayLarge,
                )
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun WeatherBottomAddBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        FloatingActionButton(
            onClick = onClick,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary,
            shape = MaterialTheme.shapes.small,
            elevation = FloatingActionButtonDefaults.elevation(),
            modifier = Modifier.padding(16.dp),
        ) {
            Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.add_city_button), modifier = Modifier.scale(2f))
        }
    }
}

@Composable
private fun WeatherExtendedBottomAddBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomStart,
    ) {
        ExtendedFloatingActionButton(
            onClick = onClick,
            text = { Text(text = stringResource(id = R.string.add_city), fontSize = 25.sp) },
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary,
            shape = MaterialTheme.shapes.small,
            icon = {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.add_city_button),
                    modifier = Modifier.scale(2f),
                )
            },
            modifier = Modifier.padding(16.dp),
        )
    }
}

private fun directionBasedOnDegrees(direction: Float): String {
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
private fun shortNotation(direction: String): String {
    var short = ""
    for (letter in direction) {
        if (letter in 'A'..'Z') {
            short += letter
        }
    }
    return short
}
