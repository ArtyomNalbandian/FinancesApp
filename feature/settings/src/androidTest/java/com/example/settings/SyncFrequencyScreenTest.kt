package com.example.settings

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SyncFrequencyScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSyncFrequencyTitleDisplayed() {
        composeTestRule.setContent {
            // Mock SyncFrequencyScreen content
        }

        composeTestRule.onNodeWithText("Частота синхронизации").assertIsDisplayed()
    }

    @Test
    fun testFrequencyHoursDisplayed() {
        composeTestRule.setContent {
            // Mock SyncFrequencyScreen content
        }

        composeTestRule.onNodeWithText("8 ч.").assertIsDisplayed()
    }

    @Test
    fun testFrequencyHintDisplayed() {
        composeTestRule.setContent {
            // Mock SyncFrequencyScreen content
        }

        composeTestRule.onNodeWithText("Выберите, как часто приложение будет синхронизироваться с сервером.").assertIsDisplayed()
    }

    @Test
    fun testBackButtonClickable() {
        composeTestRule.setContent {
            // Mock SyncFrequencyScreen content
        }

        composeTestRule.onNodeWithText("Назад").performClick()
    }

    @Test
    fun testSliderDisplayed() {
        composeTestRule.setContent {
            // Mock SyncFrequencyScreen content
        }

        composeTestRule.onNodeWithText("Слайдер").assertIsDisplayed()
    }
} 