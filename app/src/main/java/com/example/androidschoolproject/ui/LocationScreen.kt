import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.androidschoolproject.ui.WeatherApp
import com.google.android.gms.location.LocationServices

// package com.example.androidschoolproject.ui
//
// import android.content.Context
// import android.content.pm.PackageManager
// import androidx.activity.compose.rememberLauncherForActivityResult
// import androidx.activity.result.contract.ActivityResultContracts
// import androidx.compose.foundation.layout.Arrangement
// import androidx.compose.foundation.layout.Column
// import androidx.compose.foundation.layout.Spacer
// import androidx.compose.foundation.layout.fillMaxSize
// import androidx.compose.foundation.layout.height
// import androidx.compose.foundation.layout.padding
// import androidx.compose.material3.Button
// import androidx.compose.material3.Text
// import androidx.compose.runtime.Composable
// import androidx.compose.runtime.getValue
// import androidx.compose.runtime.mutableStateOf
// import androidx.compose.runtime.remember
// import androidx.compose.runtime.setValue
// import androidx.compose.ui.Alignment
// import androidx.compose.ui.Modifier
// import androidx.compose.ui.platform.LocalContext
// import androidx.compose.ui.unit.dp
// import androidx.core.content.ContextCompat
// import com.google.android.gms.location.LocationServices
//
// @Composable
// fun LocationScreen(weatherUiState: WeatherUiState, onPermissionGranted: () -> Unit) {
//    val context = LocalContext.current
//    var location by remember { mutableStateOf("Your location") }
//    var isLocationPermissionGranted by remember { mutableStateOf(false) }
//
//    // Create a permission launcher
//    val requestPermissionLauncher =
//        rememberLauncherForActivityResult(
//            contract = ActivityResultContracts.RequestPermission(),
//            onResult = { isGranted: Boolean ->
//                if (isGranted) {
//                    // Permission granted, update the location
//                    getCurrentLocation(context) { lat, long ->
//                        location = "Latitude: $lat, Longitude: $long"
//                    }
//                }
//            },
//        )
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ) {
//        Button(
//            onClick = {
//                if (hasLocationPermission(context)) {
//                    // Permission already granted, update the location
//                    getCurrentLocation(context) { lat, long ->
//                        location = "Latitude: $lat, Longitude: $long"
//                    }
//                    onPermissionGranted()
//                } else {
//                    // Request location permission
//                    requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
//                }
//            },
//        ) {
//            Text(text = "Allow")
//        }
//
//        if (isLocationPermissionGranted) {
//            Text(text = "$location")
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        Text(text = location)
//    }
// }
//
// fun hasLocationPermission(context: Context): Boolean {
//    return ContextCompat.checkSelfPermission(
//        context,
//        android.Manifest.permission.ACCESS_FINE_LOCATION,
//    ) == PackageManager.PERMISSION_GRANTED
// }
//
// fun getCurrentLocation(context: Context, callback: (Any, Any) -> Unit) {
//    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
//    fusedLocationClient.lastLocation
//        .addOnSuccessListener { location ->
//            if (location != null) {
//                val lat = location.latitude
//                val long = location.longitude
//                callback(lat, long)
//            }
//        }
//        .addOnFailureListener { exception ->
//            // Handle location retrieval failure
//            exception.printStackTrace()
//        }
// }

@Composable
fun LocationPermissionScreen(navController: NavController) {
    val context = LocalContext.current
    val hasLocationPermission by rememberUpdatedState(hasLocationPermission(context))
    if (hasLocationPermission) {
        navController.navigate("weatherApp")
    } else {
        val requestPermissionLauncher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
            ) { isGranted: Boolean ->
                if (isGranted) {
                    navController.navigate("weatherApp")
                } else {
                    // Handle permission denied case
                }
            }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                },
            ) {
                Text(text = "Allow")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Location permission required.")
        }
    }
}

// fun hasLocationPermission(context: Context): Boolean {
//    return context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
// }

fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION,
    ) == PackageManager.PERMISSION_GRANTED
}

fun getCurrentLocation(context: Context, callback: (Any, Any) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return
    }
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                val lat = location.latitude
                val long = location.longitude
                callback(lat, long)
            }
        }
        .addOnFailureListener { exception ->
            // Handle location retrieval failure
            exception.printStackTrace()
        }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AppContent(navController: NavController, windowSize: WindowWidthSizeClass) {
    val context = LocalContext.current
    val hasLocationPermission by rememberUpdatedState(hasLocationPermission(context))

    if (hasLocationPermission) {
        WeatherApp(windowSize = windowSize)
    } else {
        Text("Location permission not granted.")
        // Handle this case according to your use case
    }
}
