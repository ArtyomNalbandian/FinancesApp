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
class IncomesAddScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testAddIncomeScreenTitleDisplayed() {
        composeTestRule.setContent {
            // Mock IncomesAddScreen content
        }

        composeTestRule.onNodeWithText("Добавить доход").assertIsDisplayed()
    }

    @Test
    fun testAmountFieldEditable() {
        composeTestRule.setContent {
            // Mock IncomesAddScreen content
        }

        composeTestRule.onNodeWithText("Сумма").performTextInput("5000")
    }

    @Test
    fun testCategorySelectionDisplayed() {
        composeTestRule.setContent {
            // Mock IncomesAddScreen content
        }

        composeTestRule.onNodeWithText("Категория").assertIsDisplayed()
    }

    @Test
    fun testCreateButtonDisplayed() {
        composeTestRule.setContent {
            // Mock IncomesAddScreen content
        }

        composeTestRule.onNodeWithText("Создать").assertIsDisplayed()
    }

    @Test
    fun testCancelButtonClickable() {
        composeTestRule.setContent {
            // Mock IncomesAddScreen content
        }

        composeTestRule.onNodeWithText("Отмена").performClick()
    }
} 