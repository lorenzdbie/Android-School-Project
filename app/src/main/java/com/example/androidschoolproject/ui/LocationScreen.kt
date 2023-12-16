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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidschoolproject.R

@Composable
fun LocationPermissionScreen(goToStartScreen: () -> Unit, modifier: Modifier = Modifier) {
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                goToStartScreen()
                // Permission granted, navigate to the next screen
            } else {
                goToStartScreen()
            }
        }

    Surface(
        modifier = modifier.fillMaxSize(),
        //  color = MaterialTheme.colorScheme.primary,
    ) {
        Column(
            modifier = modifier
//                .fillMaxSize()
//                .background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            MaterialTheme.colorScheme.primary,
//                            MaterialTheme.colorScheme.secondary,
//                        ),
//                    ),
//                )
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.location_on),
                contentDescription = null,
                modifier = Modifier.size(200.dp),
            )
            Spacer(modifier = Modifier.weight(0.5f))
            Text(
                text = "Would you like to view the weather at your location?",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp),
            )
            Text(text = "Start sharing your location with us")
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick =
                // Request location permission again when the button is clicked
                { requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION) },
            ) {
                Text(text = "Share location")
            }
            Button(
                onClick = goToStartScreen,
                colors = ButtonDefaults.buttonColors(Color.Transparent),
            ) {
                Text(text = "Maybe later", modifier = Modifier.background(Color.Transparent))
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
