package com.example.androidschoolproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidschoolproject.ui.LocationPermissionScreen
import com.example.androidschoolproject.ui.OverviewScreen
import com.example.androidschoolproject.ui.WeatherApp
import com.example.androidschoolproject.ui.theme.AndroidSchoolProjectTheme
import com.example.androidschoolproject.ui.utils.hasLocationPermission

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidSchoolProjectTheme {
                val navController = rememberNavController()
                val windowSize = calculateWindowSizeClass(this)

               // val goToLocationScreen = { navController.navigate(OverviewScreen.Location.name) }
                val goToWeatherApp = { navController.navigate(OverviewScreen.Start.name) }

                val screen = if (this.hasLocationPermission()) OverviewScreen.Start.name else OverviewScreen.Location.name

                // A surface container using the 'background' color from the theme
                Surface {
                    NavHost(navController, startDestination = screen) {
                        composable(OverviewScreen.Location.name) {
                            LocationPermissionScreen { goToWeatherApp() }
                        }
                        composable(OverviewScreen.Start.name) {
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
        WeatherApp(locationEnabled = true, windowSize = windowSize)
    } else {
        WeatherApp(locationEnabled = false, windowSize = windowSize)
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherAppCompactPreview() {
    AndroidSchoolProjectTheme {
        Surface {
            WeatherApp(locationEnabled = true,
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
            WeatherApp(locationEnabled = true,
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
            WeatherApp(locationEnabled = true,
                windowSize = WindowWidthSizeClass.Expanded,
            )
        }
    }
}
