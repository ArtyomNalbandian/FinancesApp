package com.example.settings

import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDarkThemeToggle() {
        composeTestRule.setContent {
            SettingsScreen(
                navigateToColorPicker = {},
                navigateToHaptics = {},
                navigateToPinCode = {},
                navigateToSyncFrequency = {},
                navigateToLanguageSwitch = {},
                navigateToAppInfo = {}
            )
        }

        composeTestRule.onNodeWithText("Темная тема").assertExists()

        composeTestRule.onNodeWithText("Темная тема").assertIsOff()

        composeTestRule.onNodeWithText("Темная тема").performClick()

        composeTestRule.onNodeWithText("Темная тема").assertIsOn()
    }

    @Test
    fun testSettingsItemsExist() {
        composeTestRule.setContent {
            SettingsScreen(
                navigateToColorPicker = {},
                navigateToHaptics = {},
                navigateToPinCode = {},
                navigateToSyncFrequency = {},
                navigateToLanguageSwitch = {},
                navigateToAppInfo = {}
            )
        }

        composeTestRule.onNodeWithText("Темная тема").assertExists()
        composeTestRule.onNodeWithText("Основной цвет").assertExists()
        composeTestRule.onNodeWithText("Вибрация").assertExists()
        composeTestRule.onNodeWithText("Пин-код").assertExists()
        composeTestRule.onNodeWithText("Синхронизация").assertExists()
        composeTestRule.onNodeWithText("Язык").assertExists()
        composeTestRule.onNodeWithText("О приложении").assertExists()
    }

    @Test
    fun testColorPickerNavigation() {
        composeTestRule.setContent {
            SettingsScreen(
                navigateToColorPicker = {},
                navigateToHaptics = {},
                navigateToPinCode = {},
                navigateToSyncFrequency = {},
                navigateToLanguageSwitch = {},
                navigateToAppInfo = {}
            )
        }

        composeTestRule.onNodeWithText("Основной цвет").performClick()
    }

    @Test
    fun testHapticsNavigation() {
        composeTestRule.setContent {
            SettingsScreen(
                navigateToColorPicker = {},
                navigateToHaptics = {},
                navigateToPinCode = {},
                navigateToSyncFrequency = {},
                navigateToLanguageSwitch = {},
                navigateToAppInfo = {}
            )
        }

        composeTestRule.onNodeWithText("Вибрация").performClick()
    }

    @Test
    fun testPinCodeNavigation() {
        composeTestRule.setContent {
            SettingsScreen(
                navigateToColorPicker = {},
                navigateToHaptics = {},
                navigateToPinCode = {},
                navigateToSyncFrequency = {},
                navigateToLanguageSwitch = {},
                navigateToAppInfo = {}
            )
        }

        composeTestRule.onNodeWithText("Пин-код").performClick()
    }
}
