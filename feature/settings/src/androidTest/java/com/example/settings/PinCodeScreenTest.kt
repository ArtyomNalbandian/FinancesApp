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
    fun testPinCodeInput() {
        composeTestRule.setContent {
            PinCodeScreen(
                mode = PinCodeMode.Set,
                onSuccess = {},
                navigateBack = {}
            )
        }

        composeTestRule.onNodeWithText("Создать пин-код").assertIsDisplayed()

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("4").performClick()

        composeTestRule.onNodeWithText("OK").assertIsDisplayed()
    }

    @Test
    fun testPinCodeDelete() {
        composeTestRule.setContent {
            PinCodeScreen(
                mode = PinCodeMode.Set,
                onSuccess = {},
                navigateBack = {}
            )
        }

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("2").performClick()

        composeTestRule.onNodeWithText("←").performClick()

        composeTestRule.onNodeWithText("OK").assertIsDisplayed()
    }

    @Test
    fun testPinCodeCheckMode() {
        composeTestRule.setContent {
            PinCodeScreen(
                mode = PinCodeMode.Check,
                onSuccess = {},
                navigateBack = {}
            )
        }

        composeTestRule.onNodeWithText("Введите пин-код").assertIsDisplayed()

        composeTestRule.onNodeWithText("Установить новый пин-код").assertIsDisplayed()
        composeTestRule.onNodeWithText("Изменить пин-код").assertIsDisplayed()
    }
}
