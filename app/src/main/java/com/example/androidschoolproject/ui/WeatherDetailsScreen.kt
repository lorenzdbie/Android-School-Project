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
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
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

/**
 * tThe main DetailsWeatherScreen composable. It displays the weather details of a city.
 * @param weatherUiState the state of the weather details screen
 * @param onBackPressed the callback to be called when the back button is pressed
 * @param isFullScreen whether the screen is full screen or not
 */
@Composable
fun DetailsWeatherScreen(
    weatherUiState: WeatherUiState,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false,
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
                LazyColumn {
                    item { WeatherDetails(city = city) }
                    item { Divider() }
                    item { PollutionDetails(city = city) }
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(
                        id = R.string.lastUpdated,
                        "${formatToLocalDateTime(dateString = city.weather.timeStamp)}",
                    ),
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally,
                    ),
                )
            }
        }
    }
}

/**
 * The header of the weather details screen
 * @param city the city to display
 */
@Composable
fun DetailHeader(city: WeatherCity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.header_padding_small)),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(stringResource(id = R.string.country_label, city.country))
        Text(stringResource(id = R.string.state_label, city.state))
        Text(stringResource(id = R.string.country_label, city.city))
    }
}

/**
 * The weather details of a city
 * @param city the city to display
 */
@Composable
fun WeatherDetails(city: WeatherCity) {
    Column {
        Text("Weather", modifier = Modifier.align(Alignment.CenterHorizontally))
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End,
            ) {
                DetailCard(
                    detailTitle = R.string.temperature,
                    value = city.weather.temperature,
                    unit = stringResource(id = R.string.temperature_unit),
                )
                Wind(city = city)
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.card_padding)))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
            ) {
                WeatherIconCard(city = city)
                DetailCard(
                    detailTitle = R.string.humidity,
                    value = city.weather.humidity,
                    unit = stringResource(id = R.string.humidity_unit),
                )
                DetailCard(
                    detailTitle = R.string.pressure,
                    value = city.weather.atmosphericPressure,
                    unit = " ${stringResource(id = R.string.pressure_unit)}",
                )
            }
        }
    }
}

/**
 * A card displaying a weather detail
 * @param detailTitle the title of the detail
 * @param value the value of the detail
 * @param unit the unit of the detail
 */
@Composable
fun DetailCard(@StringRes detailTitle: Int, value: Number, unit: String) {
    Card(
        modifier = Modifier
            .sizeIn(maxWidth = dimensionResource(id = R.dimen.max_detailCard_width))
            .padding(dimensionResource(id = R.dimen.card_padding)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.card_padding)),
        ) {
            Text(text = stringResource(id = detailTitle))
            Text(
                "$value$unit",
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }
}

/**
 * The wind details of a city
 * @param city the city to display
 */
@Composable
fun Wind(city: WeatherCity) {
    Card(
        modifier = Modifier
            .sizeIn(maxWidth = dimensionResource(id = R.dimen.max_detailCard_width))
            .padding(dimensionResource(id = R.dimen.card_padding)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.card_padding)),
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

/**
 * The weather icon of a city
 * @param city the city to display
 */
@Composable
fun WeatherIconCard(city: WeatherCity) {
    Card(
        modifier = Modifier
            .sizeIn(maxWidth = dimensionResource(id = R.dimen.max_detailCard_width))
            .padding(dimensionResource(id = R.dimen.card_padding)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.card_padding)),
            Arrangement.Center,
            Alignment.CenterHorizontally,
        ) {
            WeatherIcon(icon = city.weather.weatherIcon, viewSize = ViewSize.LARGE)
        }
    }
}

/**
 * The pollution details of a city
 * @param city the city to display
 */
@Composable
fun PollutionDetails(city: WeatherCity) {
    Column {
        Text("Air Quality", modifier = Modifier.align(Alignment.CenterHorizontally))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End,
            ) {
                DetailCard(
                    detailTitle = R.string.us_standard,
                    value = city.pollution.aqiUsa,
                    unit = " ${pollutionUnit(city.pollution.mainUsa)}",
                )
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.card_padding)))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start,
            ) {
                DetailCard(
                    detailTitle = R.string.china_standard,
                    value = city.pollution.aqiChina,
                    unit = " ${pollutionUnit(city.pollution.mainChina)}",
                )
            }
        }
    }
}

/**
 * The wind direction of a city
 * @param windDirection the direction of the wind
 */
@Composable
private fun WindDirectionRose(windDirection: Int) {
    Box(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.windrose_padding)),
    ) {
        Image(
            painter = painterResource(id = R.drawable.compass),
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.windrose_size))
                .clip(CircleShape)
                .scale(1.2f)
                .graphicsLayer(
                    rotationZ = windDirection - 32f,
                    transformOrigin = TransformOrigin(0.5f, 0.5f),
                )
                .align(Alignment.Center),
        )
    }
}
