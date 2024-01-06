package com.example.androidschoolproject.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidschoolproject.R

/**
 *This is the first screen where the user grants of dismisses location permissions for the weather App
 * @param goToStartScreen a variable to navigate to the weatherApp start screen
 */
@Composable
fun LocationPermissionScreen(goToStartScreen: () -> Unit, modifier: Modifier = Modifier) {
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            goToStartScreen()
        } else {
            goToStartScreen()
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.location_on),
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size_xlarge)),
            )
            Spacer(modifier = Modifier.weight(0.5f))
            Text(
                text = stringResource(id = R.string.ask_sharing),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium)),
            )
            Text(text = stringResource(id = R.string.start_sharing))
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION) },
            ) {
                Text(text = stringResource(id = R.string.share_location))
            }
            Button(
                onClick = goToStartScreen,
                colors = ButtonDefaults.buttonColors(Color.Transparent),
            ) {
                Text(text = stringResource(id = R.string.decline_sharing), modifier = Modifier.background(Color.Transparent))
            }
            Spacer(modifier = Modifier.weight(0.3f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationPermissionScreenPreview() {
    Surface {
        LocationPermissionScreen({})
    }
}
