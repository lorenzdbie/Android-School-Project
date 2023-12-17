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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
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
import com.example.androidschoolproject.ui.utils.getWeatherIcon
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun WeatherHomeScreen(
    contentType: WeatherContentType,
    weatherUiState: WeatherUiState,
    apiUiState: ApiUiState,
    onCityCardPressed: (WeatherCity) -> Unit,
    onCityCardDelete: (WeatherCity) -> Unit,
    onDetailScreenBackPressed: () -> Unit,
    collectCountries: () -> Unit,
    collectStates: (String) -> Unit,
    collectCities: (String, String) -> Unit,
    onCitySelect: (String) -> Unit,
    onClickAddCity: (String, String, String) -> Unit,
    onAddCityScreenPressed: () -> Unit,
    onAddCityClosedPressed: () -> Unit,
    onDismissError: () -> Unit,
    onRefreshContent: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,

) {
    if (contentType == WeatherContentType.LIST_AND_DETAIL) {
        WeatherAppContent(
            contentType = contentType,
            weatherUiState = weatherUiState,
            onCityCardPressed = onCityCardPressed,
            onCityCardDelete = onCityCardDelete,
            onAddCityPressed = onAddCityScreenPressed,
            onRefreshContent = onRefreshContent,
            isLoading = isLoading,
            modifier = modifier,
        )
    } else {
        if (weatherUiState.isShowingHomepage) {
            WeatherAppContent(
                contentType = contentType,
                weatherUiState = weatherUiState,
                onCityCardPressed = onCityCardPressed,
                onAddCityPressed = onAddCityScreenPressed,
                onCityCardDelete = onCityCardDelete,
                onRefreshContent = onRefreshContent,
                isLoading = isLoading,
                modifier = modifier,

            )
        } else {
            Column(modifier = modifier) {
                WeatherTopAppBar(modifier = modifier)
                DetailsWeatherScreen(
                    weatherUiState = weatherUiState,
                    onBackPressed = onDetailScreenBackPressed,
                    isFullScreen = true,
                    modifier = modifier,
                )
            }
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
                    weatherUiState = weatherUiState,
                    onClosePressed = onAddCityClosedPressed,
                    collectCountries = collectCountries,
                    collectStates = collectStates,
                    collectCities = collectCities,
                    onCitySelect = onCitySelect,
                    onClickAddCity = onClickAddCity,
                    modifier = Modifier.weight(1f),
                )
            }
        } else {
            AddCityScreen(
                weatherUiState = weatherUiState,
                onClosePressed = onAddCityClosedPressed,
                collectCountries = collectCountries,
                collectStates = collectStates,
                onCitySelect = onCitySelect,
                collectCities = collectCities,
                onClickAddCity = onClickAddCity,
            )
        }
    }
    if (apiUiState == ApiUiState.Error) {
        AlertDialog(
            onDismissRequest = onDismissError,
            title = { Text(text = stringResource(id = R.string.error_title)) },
            text = { Text(text = stringResource(id = R.string.error_text)) },
            confirmButton = {
                Button(onClick = onDismissError) {
                    Text(text = stringResource(id = R.string.button_close))
                }
            },
        )
    }
}

@Composable
fun WeatherAppContent(
    contentType: WeatherContentType,
    weatherUiState: WeatherUiState,
    onCityCardPressed: (WeatherCity) -> Unit,
    onAddCityPressed: () -> Unit,
    modifier: Modifier = Modifier,
    onCityCardDelete: (WeatherCity) -> Unit,
    onRefreshContent: () -> Unit,
    isLoading: Boolean,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier.fillMaxSize()) {
            WeatherTopAppBar(modifier = modifier.fillMaxWidth())
            if (contentType == WeatherContentType.LIST_AND_DETAIL) {
                WeatherListAndDetailsContent(
                    weatherUiState = weatherUiState,
                    onCityCardPressed = onCityCardPressed,
                    onCityCardDelete = onCityCardDelete,
                    onRefreshContent = onRefreshContent,
                    isLoading = isLoading,
                    modifier = modifier,
                )
            } else {
                Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    //          Text(text = "Nr of updates: ${weatherUiState.numberOfUpdates}")
                    //         Text(text = "Nr of calls: ${weatherUiState.nrOfCalls}")
                }
                WeatherOnlyListContent(
                    weatherUiState = weatherUiState,
                    onCityCardPressed = onCityCardPressed,
                    onCityCardDelete = onCityCardDelete,
                    onRefreshContent = onRefreshContent,
                    isLoading = isLoading,
                    modifier = modifier,
                )
            }
        }
    }
    if (contentType == WeatherContentType.LIST_AND_DETAIL) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
            WeatherExtendedBottomAddBar(onClick = onAddCityPressed, modifier = modifier)
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            WeatherBottomAddBar(onClick = onAddCityPressed, modifier = modifier)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherOnlyListContent(
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
            if (localCity != null) {
                cities.add(localCity)
            }
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
                            modifier = Modifier.padding(5.dp),
                        )
                    } else {
                        SwipeToDismiss(state = dismissState, background = {
                            DismissBackground(dismissState = dismissState)
                        }, directions = setOf(DismissDirection.EndToStart), dismissContent = {
                            CityWeatherCard(
                                city = city,
                                selected = if (showSelected) weatherUiState.currentCity?.id == city.id else false,
                                onCardClick = { onCityCardPressed(city) },
                                modifier = Modifier.padding(5.dp),
                            )
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherListAndDetailsContent(
    weatherUiState: WeatherUiState,
    onCityCardPressed: (WeatherCity) -> Unit,
    onCityCardDelete: (WeatherCity) -> Unit,
    onRefreshContent: () -> Unit,
    modifier: Modifier = Modifier,
    showSelected: Boolean = true,
    isLoading: Boolean,
) {
    val weatherContentDescription = stringResource(R.string.listAndDetailsContent)
    val activity = LocalContext.current as Activity

    Row(modifier = modifier.testTag(weatherContentDescription)) {
        //   Column(modifier = Modifier.weight(0.7f)) {
        //       Text(text = "Nr of updates: ${weatherUiState.numberOfUpdates}")
        WeatherOnlyListContent(
            weatherUiState = weatherUiState,
            onCityCardPressed = onCityCardPressed,
            onCityCardDelete = onCityCardDelete,
            onRefreshContent = onRefreshContent,
            showSelected = showSelected,
            isLoading = isLoading,
            modifier = modifier.weight(0.7f),
        )

        DetailsWeatherScreen(
            weatherUiState = weatherUiState,
            onBackPressed = { activity.finish() },
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
fun WeatherIcon(icon: String, viewSize: ViewSize, modifier: Modifier = Modifier) {
    @DrawableRes val weatherIcon = getWeatherIcon(icon)
    Box(
        modifier = Modifier.requiredSize(
            when (viewSize) {
                ViewSize.SMALL -> 50.dp
                ViewSize.LARGE -> 120.dp
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
        Row(
            modifier = modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.weight(0.1f))
            if (isLocalCity) {
                Icon(
                    painter = painterResource(id = R.drawable.near_me),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 2.dp),
                )
            }
            Text(
                text = city.city,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(0.6f),
            )
            //  Spacer(modifier = Modifier.weight(0.2f))
//            Spacer(modifier = Modifier.weight(0.1f))
            Text(
                text = "${city.id}",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.weight(0.1f))
            Column(
                modifier = Modifier.padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Temperature(temp = city.weather.temperature, size = ViewSize.SMALL)
                Spacer(modifier = Modifier.height(5.dp))
                WindDirection(direction = city.weather.windDirection, size = ViewSize.SMALL)
            }
            WeatherIcon(
                icon = city.weather.weatherIcon,
                viewSize = ViewSize.SMALL,
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(horizontal = 10.dp),
            )
            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
}

@Composable
fun WeatherTopAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
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
            Icon(
                Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.add_city_button),
                modifier = Modifier.scale(2f),
            )
        }
    }
}

@Composable
private fun WeatherExtendedBottomAddBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier.padding(0.dp),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: DismissState) {
    val color = when (dismissState.dismissDirection) {
        DismissDirection.StartToEnd -> Color(0xFF1DE9B6)
        DismissDirection.EndToStart -> MaterialTheme.colorScheme.error
        null -> Color.Transparent
    }
    val direction = dismissState.dismissDirection
    Row(
        modifier = Modifier
            .padding(5.dp)
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
                contentDescription = "delete",
            )
        }
    }
}
