package com.example.edit_account

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditAccountScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testEditAccountScreenTitleDisplayed() {
        composeTestRule.setContent {
            // Mock EditAccountScreen content
        }

        composeTestRule.onNodeWithText("Редактировать счет").assertIsDisplayed()
    }

    @Test
    fun testAccountNameFieldEditable() {
        composeTestRule.setContent {
            // Mock EditAccountScreen content
        }

        composeTestRule.onNodeWithText("Название счета").performTextInput("Новый счет")
    }

    @Test
    fun testBalanceFieldEditable() {
        composeTestRule.setContent {
            // Mock EditAccountScreen content
        }

        composeTestRule.onNodeWithText("Баланс").performTextInput("1000")
    }

    @Test
    fun testSaveButtonDisplayed() {
        composeTestRule.setContent {
            // Mock EditAccountScreen content
        }

        composeTestRule.onNodeWithText("Сохранить").assertIsDisplayed()
    }

    @Test
    fun testCancelButtonClickable() {
        composeTestRule.setContent {
            // Mock EditAccountScreen content
        }

        composeTestRule.onNodeWithText("Отмена").performClick()
    }
} 