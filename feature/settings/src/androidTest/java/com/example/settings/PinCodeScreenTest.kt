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
class PinCodeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testPinCodeScreenTitleDisplayed() {
        composeTestRule.setContent {
            // Mock PinCodeScreen content
        }

        composeTestRule.onNodeWithText("Пин-код").assertIsDisplayed()
    }

    @Test
    fun testPinCreateTextDisplayed() {
        composeTestRule.setContent {
            // Mock PinCodeScreen content
        }

        composeTestRule.onNodeWithText("Придумайте пин-код").assertIsDisplayed()
    }

    @Test
    fun testPinCodeDotsDisplayed() {
        composeTestRule.setContent {
            // Mock PinCodeScreen content
        }

        composeTestRule.onNodeWithText("●●●●").assertIsDisplayed()
    }

    @Test
    fun testBackButtonClickable() {
        composeTestRule.setContent {
            // Mock PinCodeScreen content
        }

        composeTestRule.onNodeWithText("Назад").performClick()
    }

    @Test
    fun testPinCodeButtonsDisplayed() {
        composeTestRule.setContent {
            // Mock PinCodeScreen content
        }

        composeTestRule.onNodeWithText("1").assertIsDisplayed()
        composeTestRule.onNodeWithText("2").assertIsDisplayed()
        composeTestRule.onNodeWithText("3").assertIsDisplayed()
    }
}
