package com.example.expenses

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
class ExpensesEditScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testExpensesEditTitleDisplayed() {
        composeTestRule.setContent {
            // Mock ExpensesEditScreen content
        }

        composeTestRule.onNodeWithText("Редактировать расход").assertIsDisplayed()
    }

    @Test
    fun testAmountFieldEditable() {
        composeTestRule.setContent {
            // Mock ExpensesEditScreen content
        }

        composeTestRule.onNodeWithText("Сумма").performTextInput("150")
    }

    @Test
    fun testCategorySelectionDisplayed() {
        composeTestRule.setContent {
            // Mock ExpensesEditScreen content
        }

        composeTestRule.onNodeWithText("Категория").assertIsDisplayed()
    }

    @Test
    fun testSaveButtonDisplayed() {
        composeTestRule.setContent {
            // Mock ExpensesEditScreen content
        }

        composeTestRule.onNodeWithText("Сохранить").assertIsDisplayed()
    }

    @Test
    fun testCancelButtonClickable() {
        composeTestRule.setContent {
            // Mock ExpensesEditScreen content
        }

        composeTestRule.onNodeWithText("Отмена").performClick()
    }
} 