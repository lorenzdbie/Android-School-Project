package com.example.androidschoolproject.ui

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.androidschoolproject.R
import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.ui.utils.Temperature
import com.example.androidschoolproject.ui.utils.ViewSize
import com.example.androidschoolproject.ui.utils.WindDirection
import com.example.androidschoolproject.ui.utils.getWeatherIcon
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

/**
 * the main WeatherListScreen composable
 * @param weatherUiState the state of the weather
 * @param onCityCardPressed the callback to be called when a city card is pressed
 * @param onCityCardDelete the callback to be called when a city card is deleted
 * @param onRefreshContent the callback to be called when the user refreshes the content
 * @param isLoading the loading state of the content
 * @param showSelected the state of the selected city
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherListScreen(
    weatherUiState: WeatherUiState,
    onCityCardPressed: (WeatherCity) -> Unit,
    onCityCardDelete: (WeatherCity) -> Unit,
    onRefreshContent: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    showSelected: Boolean = false,

) {
    Surface(modifier = modifier.fillMaxSize()) {
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = onRefreshContent,
        ) {
            val localCity = weatherUiState.localCity
            val cityList = weatherUiState.cityList
            val cities = mutableListOf<WeatherCity>()
//
            localCity?.let { cities.add(it) }
            cities.addAll(cityList)

            val weatherContentDescription = stringResource(R.string.listOnlyContent)
            LazyColumn(
                modifier = modifier.fillMaxSize()
                    .testTag(weatherContentDescription),
            ) {
                itemsIndexed(cities) { index, city ->
                    val dismissState = rememberDismissState(confirmValueChange = {
                        if (it == DismissValue.DismissedToStart) {
                            onCityCardDelete(city)
                        }
                        true
                    })
                    if (index == 0 && localCity != null) {
                        CityWeatherCard(
                            city = localCity,
                            selected = if (showSelected) weatherUiState.currentCity?.id == localCity.id else false,
                            onCardClick = { onCityCardPressed(localCity) },
                            isLocalCity = true,
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.card_padding_small)),
                        )
                    } else {
                        SwipeToDismiss(state = dismissState, background = {
                            DismissBackground(dismissState = dismissState)
                        }, directions = setOf(DismissDirection.EndToStart), dismissContent = {
                            CityWeatherCard(
                                city = city,
                                selected = if (showSelected) weatherUiState.currentCity?.id == city.id else false,
                                onCardClick = { onCityCardPressed(city) },
                                modifier = Modifier.padding(dimensionResource(id = R.dimen.card_padding_small)),
                            )
                        })
                    }
                }
            }
        }
    }
}

/**
 * the WeatherIcon composable
 * @param icon the icon to be displayed
 * @param viewSize the size of the icon
 */
@Composable
fun WeatherIcon(icon: String, viewSize: ViewSize, modifier: Modifier = Modifier) {
    @DrawableRes val weatherIcon = getWeatherIcon(icon)
    Box(
        modifier = Modifier.requiredSize(
            when (viewSize) {
                ViewSize.SMALL -> dimensionResource(id = R.dimen.icon_size_small)
                ViewSize.LARGE -> dimensionResource(id = R.dimen.icon_size_large)
            },
        ),
    ) {
        Image(
            painter = painterResource(id = weatherIcon),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.FillWidth,

        )
    }
}

/**
 * the CityWeatherCard composable
 * @param city the city to be displayed
 * @param selected the state of the city
 * @param onCardClick the callback to be called when the card is pressed
 * @param isLocalCity the state of the city
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CityWeatherCard(
    city: WeatherCity,
    selected: Boolean,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLocalCity: Boolean = false,
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.card_padding)),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.card_padding)),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.tertiaryContainer
            } else {
                MaterialTheme.colorScheme.primaryContainer
            },
        ),
        onClick = onCardClick,
    ) {
        Row(
            modifier = modifier.padding(
                horizontal = dimensionResource(id = R.dimen.card_padding),
                vertical = dimensionResource(id = R.dimen.card_padding_small),
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.weight(0.1f))
            if (isLocalCity) {
                Icon(
                    painter = painterResource(id = R.drawable.near_me),
                    contentDescription = null,
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.icon_padding)),
                )
            }
            Text(
                text = city.city,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(0.6f),
            )
            Text(
                text = "${city.id}",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.weight(0.1f))
            Column(
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.card_padding)),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Temperature(temp = city.weather.temperature, size = ViewSize.SMALL)
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.card_padding_small)))
                WindDirection(direction = city.weather.windDirection, size = ViewSize.SMALL)
            }
            WeatherIcon(
                icon = city.weather.weatherIcon,
                viewSize = ViewSize.SMALL,
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(horizontal = dimensionResource(id = R.dimen.card_padding)),
            )
            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
}

/**
 * the DismissBackground composable
 * @param dismissState the state of the dismiss
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: DismissState) {
    val color = when (dismissState.dismissDirection) {
        DismissDirection.EndToStart -> MaterialTheme.colorScheme.error
        else -> Color.Transparent
    }
    val direction = dismissState.dismissDirection
    Row(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.card_padding_small))
            .clip(MaterialTheme.shapes.medium)
            .fillMaxSize()
            .background(color),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Spacer(modifier = Modifier)
        if (direction == DismissDirection.EndToStart) {
            Icon(
                Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.delete),
            )
        }
    }
}
