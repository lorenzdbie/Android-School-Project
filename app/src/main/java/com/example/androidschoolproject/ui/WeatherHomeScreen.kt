package com.example.androidschoolproject.ui

import android.app.Activity
import androidx.annotation.DrawableRes
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidschoolproject.R
import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.ui.utils.Temperature
import com.example.androidschoolproject.ui.utils.ViewSize
import com.example.androidschoolproject.ui.utils.WeatherContentType
import com.example.androidschoolproject.ui.utils.WindDirection

@Composable
fun WeatherHomeScreen(
    contentType: WeatherContentType,
    weatherUiState: WeatherUiState,
    onCityCardPressed: (WeatherCity) -> Unit,
    onDetailScreenBackPressed: () -> Unit,
    onAddCityPressed: () -> Unit,
    onAddCityClosedPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (contentType == WeatherContentType.LIST_AND_DETAIL) {
        WeatherAppContent(
            contentType = contentType,
            weatherUiState = weatherUiState,
            onCityCardPressed = onCityCardPressed,
            onAddCityPressed = onAddCityPressed,
        )
    } else {
        if (weatherUiState.isShowingHomepage) {
            WeatherAppContent(
                contentType = contentType,
                weatherUiState = weatherUiState,
                onCityCardPressed = onCityCardPressed,
                onAddCityPressed = onAddCityPressed,
            )
        } else {
            DetailsWeatherScreen(uiState = weatherUiState, onBackPressed = onDetailScreenBackPressed, isFullScreen = true)
        }
    }
    if (weatherUiState.isShowingAddCityBox) {
        if (contentType == WeatherContentType.LIST_AND_DETAIL) {
            Row {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f)),
                )
                AddCityScreen(
                    onAddPressed = { /*TODO*/ },
                    onClosePressed = onAddCityClosedPressed,
                    modifier = Modifier.weight(1f),
                )
            }
        } else {
            AddCityScreen(
                onAddPressed = { /*TODO*/ },
                onClosePressed = onAddCityClosedPressed,
            )
        }
    }
}

@Composable
fun WeatherAppContent(
    contentType: WeatherContentType,
    weatherUiState: WeatherUiState,
    onCityCardPressed: (WeatherCity) -> Unit,
    onAddCityPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Column(modifier = modifier.fillMaxSize()) {
            WeatherTopAppBar()
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                if (contentType == WeatherContentType.LIST_AND_DETAIL) {
                    WeatherListAndDetailsContent(
                        weatherUiState = weatherUiState,
                        onCityCardPressed = onCityCardPressed,
                        modifier = Modifier.weight(1f),
                    )
                } else {
                    WeatherOnlyListContent(
                        weatherUiState = weatherUiState,
                        onCityCardPressed = onCityCardPressed,
                        modifier = Modifier.weight(1f),
                    )
                }
                if (contentType == WeatherContentType.LIST_AND_DETAIL) {
                    WeatherExtendedBottomAddBar(onClick = onAddCityPressed)
                } else {
                    WeatherBottomAddBar(onClick = onAddCityPressed)
                }
            }
        }
    }
}

@Composable
fun WeatherOnlyListContent(
    weatherUiState: WeatherUiState,
    onCityCardPressed: (WeatherCity) -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(text = "Longitude = ${weatherUiState.longitude}, latitude = ${weatherUiState.latitude}")
    val cities = weatherUiState.cityList
    val weatherContentDescription = stringResource(R.string.listOnlyContent)
    LazyColumn(modifier = modifier.testTag(weatherContentDescription)) {
        items(cities, key = { city -> city.id }) { city ->
            CityWeatherCard(
                city = city,
                selected = false,
                onCardClick = { onCityCardPressed(city) },
                modifier = Modifier.padding(5.dp),
            )
        }
    }
}

@Composable
fun WeatherListAndDetailsContent(
    weatherUiState: WeatherUiState,
    onCityCardPressed: (WeatherCity) -> Unit,
    modifier: Modifier = Modifier,
) {
    val cities = weatherUiState.cityList
    val weatherContentDescription = stringResource(R.string.listAndDetailsContent)
    Row(modifier = modifier.testTag(weatherContentDescription)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cities, key = { city -> city.id }) { city ->
                CityWeatherCard(
                    city = city,
                    selected = weatherUiState.currentCity.id == city.id,
                    onCardClick = { onCityCardPressed(city) },
                    modifier = Modifier.padding(5.dp),
                )
            }
        }
        val activity = LocalContext.current as Activity
        DetailsWeatherScreen(
            uiState = weatherUiState,
            onBackPressed = { activity.finish() },
            modifier = Modifier.weight(1f),
        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CityWeatherCard(city: WeatherCity, selected: Boolean, onCardClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.tertiaryContainer
            } else {
                MaterialTheme.colorScheme.primaryContainer
            },
        ),
        onClick = onCardClick,
    ) {
        Row(modifier = modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
            Text(text = city.city, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.fillMaxWidth(0.7f))
            Spacer(modifier = Modifier.weight(1f))
            Column(modifier = Modifier.padding(horizontal = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Temperature(temp = city.weather.temperature, size = ViewSize.SMALL)
                WindDirection(directionFloat = city.weather.windDirection, size = ViewSize.SMALL)
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
        val addButtonContentDescription = stringResource(R.string.small_add_city_button)
        FloatingActionButton(
            onClick = onClick,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary,
            shape = MaterialTheme.shapes.small,
            elevation = FloatingActionButtonDefaults.elevation(),
            modifier = Modifier
                .padding(16.dp)
                .testTag(addButtonContentDescription),
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
        val addButtonContentDescription = stringResource(R.string.extended_add_city_button)
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
            modifier = Modifier
                .padding(16.dp)
                .testTag(addButtonContentDescription),
        )
    }
}
