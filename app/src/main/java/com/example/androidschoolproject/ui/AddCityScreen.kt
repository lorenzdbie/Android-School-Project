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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun AddCityScreen(
    weatherUiState: WeatherUiState,
    onClosePressed: () -> Unit,
    collectCountries: () -> Unit,
    modifier: Modifier = Modifier,
    collectStates: (String) -> Unit,
    collectCities: (String, String) -> Unit,
    onCitySelect: (String) -> Unit,
    onClickAddCity: (String, String, String) -> Unit,
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
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(40.dp), // Set the corner radius as needed
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                AddCityHeader(onClosePressed = onClosePressed)
                AddCitySelectors(weatherUiState = weatherUiState, collectStates = collectStates, collectCities = collectCities, onCitySelect = onCitySelect)
                AddCityButton(
                    enabled = weatherUiState.countryName != "Select Country" && weatherUiState.stateName != "Select State" && weatherUiState.cityName != "Select City",
                    onClick = {
                        onClickAddCity(weatherUiState.countryName, weatherUiState.stateName, weatherUiState.cityName)
                        onClosePressed()
                    },
                )
            }
        }
    }
}

@Composable
fun AddCityButton(enabled: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Button(enabled = enabled, onClick = onClick, modifier = Modifier.padding(10.dp)) {
            Text(text = stringResource(id = R.string.add_city), fontSize = 20.sp)
        }
    }
}

@Composable
fun AddCityHeader(onClosePressed: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.add_new_city),
            fontSize = 36.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp),
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

    Column(modifier = modifier.padding(40.dp).fillMaxWidth()) {
        Row(modifier.padding(vertical = 10.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Country: ", fontSize = 20.sp, modifier = Modifier.padding(end = 30.dp))
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

        Row(modifier.padding(vertical = 10.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "State: ", fontSize = 20.sp, modifier = Modifier.padding(end = 30.dp))
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

        Row(modifier.padding(vertical = 10.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "City: ", fontSize = 20.sp, modifier = Modifier.padding(end = 30.dp))
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
                    modifier = Modifier.height(500.dp),
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

// @Composable
// fun <T> SelectionInputField(
//    items: List<T>,
//    onItemSelected: (String) -> Unit,
//    selector: (T) -> String,
//    selectedItem: String,
// ) {
//    var expanded by remember { mutableStateOf(false) }
//    var selectedItem by remember { mutableStateOf("Select Item") }
//
//    if (items.isNotEmpty()) {
//        Box(modifier = Modifier) {
//            Row(
//                modifier = Modifier.clickable { expanded = !expanded },
//                horizontalArrangement = Arrangement.Start,
//                verticalAlignment = Alignment.CenterVertically,
//            ) {
//                Text(text = selectedItem)
//                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
//                DropdownMenu(
//                    expanded = expanded,
//                    onDismissRequest = { expanded = false },
//                    modifier = Modifier.height(500.dp),
//                ) {
//                    items.forEach { item ->
//                        DropdownMenuItem(
//                            text = { Text(text = selector(item)) },
//                            onClick = {
//                                selectedItem = selector(item)
//                                onItemSelected(selector(item))
//                                expanded = false
//                            },
//                        )
//                    }
//                }
//            }
//        }
//    }
// }

// @OptIn(ExperimentalMaterial3Api::class)
// @Composable
// fun SelectionInputField(
//    weatherUiState: WeatherUiState,
//    onCountrySelected: (String) -> Unit,
// ) {
//    val countries = weatherUiState.countries
//    var expanded by remember { mutableStateOf(false) }
//    var selectedCountry by remember { mutableStateOf("Select Country") }
//
//    if (countries != null) {
//        Box(modifier = Modifier) {
//            Row(
//                modifier = Modifier.clickable { expanded = !expanded },
//                horizontalArrangement = Arrangement.Start,
//                verticalAlignment = Alignment.CenterVertically,
//            ) {
//                Text(text = selectedCountry)
//                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
//                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.height(500.dp)) {
//                    countries.forEach { country ->
//                        DropdownMenuItem(
//                            text = { Text(text = country.country) },
//                            onClick = {
//                                selectedCountry = country.country
//                                expanded = false
//                            },
//                        )
//                    }
//                }
//            }
//        }
//    }
// }

// @Preview(showBackground = true)
// @Composable
// fun AddCityPreview() {
//    AndroidSchoolProjectTheme {
//        AddCityScreen(weatherUiState = ,onAddPressed = {}, onClosePressed = {}, collectCountries = {})
//    }
// }
