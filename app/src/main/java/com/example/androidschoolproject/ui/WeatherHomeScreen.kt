package com.example.androidschoolproject.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidschoolproject.R
import com.example.androidschoolproject.model.WeatherCity
import com.example.androidschoolproject.ui.utils.WeatherContentType

/**
 * the main WeatherHomeScreen composable
 * @param contentType the type of content to be shown
 * @param weatherUiState the state of the weather
 * @param apiUiState the state of the api
 * @param onCityCardPressed the callback to be called when a city card is pressed
 * @param onCityCardDelete the callback to be called when a city card is deleted
 * @param onDetailScreenBackPressed the callback to be called when the user presses the back button on the detail screen
 * @param collectCountries the callback to be called when the user wants to collect the countries
 * @param collectStates the callback to be called when the user wants to collect the states
 * @param collectCities the callback to be called when the user wants to collect the cities
 * @param onCitySelect the callback to be called when the user selects a city
 * @param onClickAddCity the callback to be called when the user wants to add a city
 * @param onAddCityScreenPressed the callback to be called when the user wants to open the add city screen
 * @param onAddCityClosedPressed the callback to be called when the user wants to close the add city screen
 * @param onDismissError the callback to be called when the user wants to dismiss the error
 * @param onRefreshContent the callback to be called when the user wants to refresh the content
 * @param isLoading the loading state of the content
 */
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

/**
 * the main WeatherAppContent composable
 * @param contentType the type of content to be shown
 * @param weatherUiState the state of the weather
 * @param onCityCardPressed the callback to be called when a city card is pressed
 * @param onAddCityPressed the callback to be called when the user wants to open the add city screen
 * @param onCityCardDelete the callback to be called when a city card is deleted
 * @param onRefreshContent the callback to be called when the user wants to refresh the content
 * @param isLoading the loading state of the content
 */
@Composable
fun WeatherAppContent(
    contentType: WeatherContentType,
    weatherUiState: WeatherUiState,
    onCityCardPressed: (WeatherCity) -> Unit,
    onAddCityPressed: () -> Unit,
    onCityCardDelete: (WeatherCity) -> Unit,
    onRefreshContent: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
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
                WeatherListScreen(
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

/**
 * the main WeatherListAndDetailsContent composable
 * @param weatherUiState the state of the weather
 * @param onCityCardPressed the callback to be called when a city card is pressed
 * @param onCityCardDelete the callback to be called when a city card is deleted
 * @param onRefreshContent the callback to be called when the user wants to refresh the content
 * @param isLoading the loading state of the content
 * @param showSelected the state of the selected city
 */
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
        WeatherListScreen(
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

/**
 * Bar with the App logo and title
 */
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

/**
 * button to go to the add city screen
 * @param onClick triggers a viewModel function to open the AddCityScreen
 */
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

/**
 * Extended Button to go to the addCityScreen
 * @param onClick triggers a viewModel function to open the AddCityScreen
 */
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
