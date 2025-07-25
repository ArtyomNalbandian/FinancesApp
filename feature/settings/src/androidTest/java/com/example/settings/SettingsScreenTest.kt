package com.example.settings

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsOff
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
        // Запускаем экран настроек
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

        // Проверяем, что переключатель темной темы отображается
        composeTestRule.onNodeWithText("Темная тема").assertExists()
        
        // Проверяем начальное состояние (светлая тема по умолчанию)
        composeTestRule.onNodeWithText("Темная тема").assertIsOff()
        
        // Кликаем на переключатель
        composeTestRule.onNodeWithText("Темная тема").performClick()
        
        // Проверяем, что переключатель включился
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

        // Проверяем наличие всех пунктов настроек
        composeTestRule.onNodeWithText("Темная тема").assertExists()
        composeTestRule.onNodeWithText("Основной цвет").assertExists()
        composeTestRule.onNodeWithText("Вибрация").assertExists()
        composeTestRule.onNodeWithText("Пин-код").assertExists()
        composeTestRule.onNodeWithText("Синхронизация").assertExists()
        composeTestRule.onNodeWithText("Язык").assertExists()
        composeTestRule.onNodeWithText("О приложении").assertExists()
    }
} 