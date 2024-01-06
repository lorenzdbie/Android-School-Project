package com.example.androidschoolproject.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidschoolproject.R

/**
 * the main AddCityScreen composable
 */
@Composable
fun AddCityScreen(
    weatherUiState: WeatherUiState,
    onClosePressed: () -> Unit,
    collectCountries: () -> Unit,
    collectStates: (String) -> Unit,
    collectCities: (String, String) -> Unit,
    onCitySelect: (String) -> Unit,
    onClickAddCity: (String, String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    BackHandler {
        onClosePressed()
    }
    LaunchedEffect(Unit) {
        collectCountries()
    }

    val addCityScreenDescription = stringResource(R.string.add_city_screen)
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
            .testTag(addCityScreenDescription),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = modifier
                .width(400.dp)
                .padding(15.dp), // Add padding if necessary
            elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.card_elevation)),
            shape = MaterialTheme.shapes.extraSmall, // Set the corner radius as needed
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                AddCityHeader(onClosePressed = onClosePressed)
                AddCitySelectors(
                    weatherUiState = weatherUiState,
                    collectStates = collectStates,
                    collectCities = collectCities,
                    onCitySelect = onCitySelect,
                )
                AddCityButton(
                    enabled = weatherUiState.countryName != stringResource(id = R.string.select_country) && weatherUiState.stateName != stringResource(id = R.string.select_state) && weatherUiState.cityName != stringResource(id = R.string.select_city),
                    onClick = {
                        onClickAddCity(
                            weatherUiState.countryName,
                            weatherUiState.stateName,
                            weatherUiState.cityName,
                        )
                    },
                )
            }
        }
    }
}

/**
 * the AddCityButton composable
 * @param enabled the state of the button
 * @param onClick the callback to be called when the button is pressed
 */
@Composable
fun AddCityButton(enabled: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Button(enabled = enabled, onClick = onClick, modifier = Modifier.padding(dimensionResource(id = R.dimen.button_padding))) {
            Text(text = stringResource(id = R.string.add_city), fontSize = 20.sp)
        }
    }
}

/**
 * the header of the AddCityScreen
 * @param onClosePressed the callback to be called when the close button is pressed
 */
@Composable
fun AddCityHeader(onClosePressed: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(id = R.dimen.add_city_header_padding)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.add_new_city),
            fontSize = 36.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = dimensionResource(id = R.dimen.card_padding)),
        )
        IconButton(
            onClick = onClosePressed,
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.detail_topbar_close_button_padding_horizontal))
                .background(MaterialTheme.colorScheme.surface, shape = CircleShape),
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(id = R.string.navigation_close),
            )
        }
    }
}

/**
 * the AddCitySelectors composable
 * @param weatherUiState the state of the weather ui
 * @param collectStates the callback to be called when the country is chosen
 * @param collectCities the callback to be called when the state is chosen
 * @param onCitySelect the callback to be called when the city is selected
 */
@Composable
private fun AddCitySelectors(
    weatherUiState: WeatherUiState,
    modifier: Modifier = Modifier,
    collectStates: (String) -> Unit,
    collectCities: (String, String) -> Unit,
    onCitySelect: (String) -> Unit,
) {
    val countries = weatherUiState.countries
    val states = weatherUiState.states
    val cities = weatherUiState.cities

    Column(
        modifier = modifier
            .padding(start = dimensionResource(id = R.dimen.padding_xlarge), top = dimensionResource(id = R.dimen.padding_xlarge), bottom = dimensionResource(id = R.dimen.padding_xlarge), end = dimensionResource(id = R.dimen.padding_large))
            .fillMaxWidth(),
    ) {
        Row(
            modifier
                .padding(vertical = dimensionResource(id = R.dimen.card_padding))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.country_name),
                fontSize = 20.sp,
                modifier = Modifier.padding(end = dimensionResource(id = R.dimen.text_padding_medium)),
            )
            if (countries != null) {
                SelectionInputField(
                    items = countries,
                    onItemSelected = {
                        collectStates(it)
                    },
                    selectedItem = weatherUiState.countryName,
                    selector = { it.country },
                )
            }
        }

        Row(
            modifier
                .padding(vertical = dimensionResource(id = R.dimen.card_padding))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(id = R.string.state_name),
                fontSize = 20.sp,
                modifier = Modifier.padding(end = 30.dp),
            )
            if (states != null) {
                SelectionInputField(
                    items = states,
                    onItemSelected = {
                        weatherUiState.countryName?.let { it1 -> collectCities(it1, it) }
                    },
                    selectedItem = weatherUiState.stateName,
                    selector = { it.state },
                )
            }
        }

        Row(
            modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(id = R.string.city_name),
                fontSize = 20.sp,
                modifier = Modifier.padding(end = 30.dp),
            )
            if (cities != null) {
                SelectionInputField(
                    items = cities,
                    onItemSelected = { onCitySelect(it) },
                    selectedItem = weatherUiState.cityName,
                    selector = { it.city },
                )
            }
        }
    }
}

/**
 * the SelectionInputField composable
 * @param items the list of items to be shown
 * @param onItemSelected the callback to be called when an item is selected
 * @param selector the selector to select the item
 * @param selectedItem the selected item
 */
@Composable
fun <T> SelectionInputField(
    items: List<T>,
    onItemSelected: (String) -> Unit,
    selector: (T) -> String,
    selectedItem: String,
) {
    var expanded by remember { mutableStateOf(false) }

    if (items.isNotEmpty()) {
        Box(modifier = Modifier) {
            Row(
                modifier = Modifier.clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = selectedItem)
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.heightIn(max = dimensionResource(id = R.dimen.selector_height)),
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = selector(item)) },
                            onClick = {
                                onItemSelected(selector(item))
                                expanded = false
                            },
                        )
                    }
                }
            }
        }
    }
}
