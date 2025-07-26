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
class LanguageSwitchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLanguageSwitchTitleDisplayed() {
        composeTestRule.setContent {
            // Mock LanguageSwitchScreen content
        }

        composeTestRule.onNodeWithText("Язык").assertIsDisplayed()
    }

    @Test
    fun testChooseLanguageTextDisplayed() {
        composeTestRule.setContent {
            // Mock LanguageSwitchScreen content
        }

        composeTestRule.onNodeWithText("Выберите язык:").assertIsDisplayed()
    }

    @Test
    fun testRussianLanguageOptionClickable() {
        composeTestRule.setContent {
            // Mock LanguageSwitchScreen content
        }

        composeTestRule.onNodeWithText("Русский").performClick()
    }

    @Test
    fun testEnglishLanguageOptionClickable() {
        composeTestRule.setContent {
            // Mock LanguageSwitchScreen content
        }

        composeTestRule.onNodeWithText("English").performClick()
    }

    @Test
    fun testBackButtonClickable() {
        composeTestRule.setContent {
            // Mock LanguageSwitchScreen content
        }

        composeTestRule.onNodeWithText("Назад").performClick()
    }
} 