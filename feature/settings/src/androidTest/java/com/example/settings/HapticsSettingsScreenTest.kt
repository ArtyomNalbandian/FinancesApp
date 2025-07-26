package com.example.settings

import androidx.compose.ui.test.assertIsDisplayed
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
class HapticsSettingsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testHapticsSettingsTitleDisplayed() {
        composeTestRule.setContent {
            // Mock HapticsSettingsScreen content
        }

        composeTestRule.onNodeWithText("Вибрация").assertIsDisplayed()
    }

    @Test
    fun testVibrationToggleSwitch() {
        composeTestRule.setContent {
            // Mock HapticsSettingsScreen content
        }

        composeTestRule.onNodeWithText("Вибрация").assertIsOff()
        composeTestRule.onNodeWithText("Вибрация").performClick()
        composeTestRule.onNodeWithText("Вибрация").assertIsOn()
    }

    @Test
    fun testVibrationEffectTextDisplayed() {
        composeTestRule.setContent {
            // Mock HapticsSettingsScreen content
        }

        composeTestRule.onNodeWithText("Эффект вибрации:").assertIsDisplayed()
    }

    @Test
    fun testBackButtonClickable() {
        composeTestRule.setContent {
            // Mock HapticsSettingsScreen content
        }

        composeTestRule.onNodeWithText("Назад").performClick()
    }

    @Test
    fun testVibrationEffectsListDisplayed() {
        composeTestRule.setContent {
            // Mock HapticsSettingsScreen content
        }

        composeTestRule.onNodeWithText("Короткая").assertIsDisplayed()
    }
} 