package com.example.androidschoolproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidschoolproject.ui.WeatherApp
import com.example.androidschoolproject.ui.theme.AndroidSchoolProjectTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidSchoolProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    val windowSize = calculateWindowSizeClass(this)
                    WeatherApp(windowSize = windowSize.widthSizeClass)
                }
            }
        }
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
