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
class AppInfoScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testAppInfoTitleDisplayed() {
        composeTestRule.setContent {
            // Mock AppInfoScreen content
        }

        composeTestRule.onNodeWithText("Информация о приложении").assertIsDisplayed()
    }

    @Test
    fun testAppNameDisplayed() {
        composeTestRule.setContent {
            // Mock AppInfoScreen content
        }

        composeTestRule.onNodeWithText("FinancesApp").assertIsDisplayed()
    }

    @Test
    fun testAppVersionDisplayed() {
        composeTestRule.setContent {
            // Mock AppInfoScreen content
        }

        composeTestRule.onNodeWithText("Версия приложения").assertIsDisplayed()
    }

    @Test
    fun testAppDeveloperDisplayed() {
        composeTestRule.setContent {
            // Mock AppInfoScreen content
        }

        composeTestRule.onNodeWithText("Разработчик").assertIsDisplayed()
    }

    @Test
    fun testBackButtonClickable() {
        composeTestRule.setContent {
            // Mock AppInfoScreen content
        }

        composeTestRule.onNodeWithText("Назад").performClick()
    }
} 