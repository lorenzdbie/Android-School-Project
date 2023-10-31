package com.example.androidschoolproject.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidschoolproject.R
import com.example.androidschoolproject.ui.theme.AndroidSchoolProjectTheme

@Composable
fun AddCityScreen(onAddPressed: () -> Unit, onClosePressed: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f)),
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
                AddCitySelectors()
                AddCityButton(onClick = onAddPressed)
            }
        }
    }
}

@Composable
fun AddCityButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Button(onClick = onClick, modifier = Modifier.padding(10.dp)) {
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
private fun AddCitySelectors(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.padding(40.dp)) {
        Selector(text = "County")
        Selector(text = "State")
        Selector(text = "city")
    }
}

@Composable
private fun Selector(text: String, modifier: Modifier = Modifier) {
    Row(modifier.padding(vertical = 10.dp)) {
        Text(text = "$text: ", fontSize = 20.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun AddCityPreview() {
    AndroidSchoolProjectTheme {
        AddCityScreen(onAddPressed = {}, onClosePressed = {})
    }
}
