package com.example.androidschoolproject.test

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.example.androidschoolproject.R
import com.example.androidschoolproject.ui.WeatherApp
import org.junit.Rule
import org.junit.Test

class WeatherAppTest {

    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    @TestCompactWidth
    fun compactDevice_verifySmallAddButton() {
        composeTestRule.setContent {
            WeatherApp(
                locationEnabled = false,
                windowSize = WindowWidthSizeClass.Compact,
            )
        }
        // Bottom navigation is displayed
        composeTestRule.onNodeWithTagForStringId(R.string.small_add_city_button).assertExists()
    }

    @Test
    @TestMediumWidth
    fun mediumDevice_verifySmallAddButton() {
        composeTestRule.setContent {
            WeatherApp(
                locationEnabled = false,
                windowSize = WindowWidthSizeClass.Medium,
            )
        }
        // Bottom navigation is displayed
        composeTestRule.onNodeWithTagForStringId(R.string.small_add_city_button).assertExists()
    }

    @Test
    @TestExpandedWidth
    fun extendedDevice_verifyLargeAddButton() {
        composeTestRule.setContent {
            WeatherApp(
                locationEnabled = false,
                windowSize = WindowWidthSizeClass.Expanded,
            )
        }
        // Bottom navigation is displayed
        composeTestRule.onNodeWithTagForStringId(R.string.extended_add_city_button).assertExists()
    }

    @Test
    @TestCompactWidth
    fun compactDevice_verifyWeatherListOnlyScreen() {
        composeTestRule.setContent {
            WeatherApp(
                locationEnabled = false,
                windowSize = WindowWidthSizeClass.Compact,
            )
        }
        // Bottom navigation is displayed
        composeTestRule.onNodeWithTagForStringId(R.string.listOnlyContent).assertExists()
    }

    @Test
    @TestMediumWidth
    fun mediumDevice_verifyWeatherOnlyListContent() {
        composeTestRule.setContent {
            WeatherApp(
                locationEnabled = false,
                windowSize = WindowWidthSizeClass.Medium,
            )
        }
        // Bottom navigation is displayed
        composeTestRule.onNodeWithTagForStringId(R.string.listOnlyContent).assertExists()
    }

    @Test
    @TestExpandedWidth
    fun extendedDevice_verifyWeatherListAndDetailsContent() {
        composeTestRule.setContent {
            WeatherApp(
                locationEnabled = false,
                windowSize = WindowWidthSizeClass.Expanded,
            )
        }
        // Bottom navigation is displayed
        composeTestRule.onNodeWithTagForStringId(R.string.listAndDetailsContent).assertExists()
    }

    @Test
    @TestCompactWidth
    fun compactDevice_addCityButtonPressed_verifyAddCityScreenShowing() {
        composeTestRule.setContent {
            WeatherApp(
                locationEnabled = false,
                windowSize = WindowWidthSizeClass.Compact,
            )
        }
        composeTestRule.onNodeWithTagForStringId(R.string.add_city_screen).assertDoesNotExist()
        composeTestRule.onNodeWithTagForStringId(R.string.small_add_city_button).performClick()
        composeTestRule.onNodeWithTagForStringId(R.string.add_city_screen).assertExists()
    }

    @Test
    @TestMediumWidth
    fun mediumDevice_addCityButtonPressed_verifyAddCityScreenShowing() {
        composeTestRule.setContent {
            WeatherApp(
                locationEnabled = false,
                windowSize = WindowWidthSizeClass.Compact,
            )
        }
        composeTestRule.onNodeWithTagForStringId(R.string.add_city_screen).assertDoesNotExist()
        composeTestRule.onNodeWithTagForStringId(R.string.small_add_city_button).performClick()
        composeTestRule.onNodeWithTagForStringId(R.string.add_city_screen).assertExists()
    }

    @Test
    @TestExpandedWidth
    fun extendedDevice_addCityButtonPressed_verifyAddCityScreenShowing() {
        composeTestRule.setContent {
            WeatherApp(
                locationEnabled = false,
                windowSize = WindowWidthSizeClass.Compact,
            )
        }
        composeTestRule.onNodeWithTagForStringId(R.string.add_city_screen).assertDoesNotExist()
        composeTestRule.onNodeWithTagForStringId(R.string.extended_add_city_button).performClick()
        composeTestRule.onNodeWithTagForStringId(R.string.add_city_screen).assertExists()
    }
}
