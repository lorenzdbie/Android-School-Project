package com.example.androidschoolproject

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidschoolproject.model.hasLocationPermission
import com.example.androidschoolproject.ui.LocationPermissionScreen
import com.example.androidschoolproject.ui.WeatherApp
import com.example.androidschoolproject.ui.theme.AndroidSchoolProjectTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidSchoolProjectTheme {
                val navController = rememberNavController()
                val windowSize = calculateWindowSizeClass(this)
                val location = ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                ).takeIf { it == PackageManager.PERMISSION_GRANTED }?.let { "weatherApp" } ?: "locationPermission"
                // A surface container using the 'background' color from the theme
                Surface {
                    NavHost(navController, startDestination = location) {
                        composable("locationPermission") {
                            LocationPermissionScreen(navController = navController)
                        }
                        composable("weatherApp") {
                            AppContent(
                                windowSize = windowSize.widthSizeClass,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppContent(windowSize: WindowWidthSizeClass) {
    val context = LocalContext.current

    if (context.hasLocationPermission()) {
        WeatherApp(windowSize = windowSize)
    } else {
        Text("Location permission not granted.")
        // Handle this case according to your use case
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherAppCompactPreview() {
    AndroidSchoolProjectTheme {
        Surface {
            WeatherApp(
                windowSize = WindowWidthSizeClass.Compact,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun WeatherAppMediumPreview() {
    AndroidSchoolProjectTheme {
        Surface {
            WeatherApp(
                windowSize = WindowWidthSizeClass.Medium,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 1400)
@Composable
fun WeatherAppExpandedPreview() {
    AndroidSchoolProjectTheme {
        Surface {
            WeatherApp(
                windowSize = WindowWidthSizeClass.Expanded,
            )
        }
    }
}
