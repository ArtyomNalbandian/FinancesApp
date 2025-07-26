package com.example.incomes

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
class IncomesEditScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIncomesEditTitleDisplayed() {
        composeTestRule.setContent {
            // Mock IncomesEditScreen content
        }

        composeTestRule.onNodeWithText("Редактировать доход").assertIsDisplayed()
    }

    @Test
    fun testAmountFieldEditable() {
        composeTestRule.setContent {
            // Mock IncomesEditScreen content
        }

        composeTestRule.onNodeWithText("Сумма").performTextInput("6000")
    }

    @Test
    fun testCategorySelectionDisplayed() {
        composeTestRule.setContent {
            // Mock IncomesEditScreen content
        }

        composeTestRule.onNodeWithText("Категория").assertIsDisplayed()
    }

    @Test
    fun testSaveButtonDisplayed() {
        composeTestRule.setContent {
            // Mock IncomesEditScreen content
        }

        composeTestRule.onNodeWithText("Сохранить").assertIsDisplayed()
    }

    @Test
    fun testCancelButtonClickable() {
        composeTestRule.setContent {
            // Mock IncomesEditScreen content
        }

        composeTestRule.onNodeWithText("Отмена").performClick()
    }
} 