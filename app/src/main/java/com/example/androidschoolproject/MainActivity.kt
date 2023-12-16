package com.example.androidschoolproject

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidSchoolProjectTheme {
                val navController = rememberNavController()
                val windowSize = calculateWindowSizeClass(this)

                val goToWeatherApp = { navController.navigate(OverviewScreen.Start.name) }

                val screen =
                    if (this.hasLocationPermission()) OverviewScreen.Start.name else OverviewScreen.Location.name

                val modifier: Modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary,
                            ),
                        ),
                    )

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = modifier,
                ) {
                    NavHost(navController, startDestination = screen) {
                        composable(OverviewScreen.Location.name) {
                            LocationPermissionScreen(
                                goToStartScreen = goToWeatherApp,
                                modifier = modifier,
                            )
                        }
                        composable(OverviewScreen.Start.name) {
                            AppContent(
                                windowSize = windowSize.widthSizeClass,
                                modifier = modifier,
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppContent(windowSize: WindowWidthSizeClass, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    if (context.hasLocationPermission()) {
        WeatherApp(locationEnabled = true, windowSize = windowSize, modifier = modifier)
    } else {
        WeatherApp(locationEnabled = false, windowSize = windowSize, modifier = modifier)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun WeatherAppCompactPreview() {
    AndroidSchoolProjectTheme {
        Surface {
            WeatherApp(
                locationEnabled = true,
                windowSize = WindowWidthSizeClass.Compact,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, widthDp = 700)
@Composable
fun WeatherAppMediumPreview() {
    AndroidSchoolProjectTheme {
        Surface {
            WeatherApp(
                locationEnabled = true,
                windowSize = WindowWidthSizeClass.Medium,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, widthDp = 1400)
@Composable
fun WeatherAppExpandedPreview() {
    AndroidSchoolProjectTheme {
        Surface {
            WeatherApp(
                locationEnabled = true,
                windowSize = WindowWidthSizeClass.Expanded,
            )
        }
    }
}
