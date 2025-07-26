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
class ExpensesAddScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testAddExpenseScreenTitleDisplayed() {
        composeTestRule.setContent {
            // Mock ExpensesAddScreen content
        }

        composeTestRule.onNodeWithText("Добавить расход").assertIsDisplayed()
    }

    @Test
    fun testAmountFieldEditable() {
        composeTestRule.setContent {
            // Mock ExpensesAddScreen content
        }

        composeTestRule.onNodeWithText("Сумма").performTextInput("100")
    }

    @Test
    fun testCategorySelectionDisplayed() {
        composeTestRule.setContent {
            // Mock ExpensesAddScreen content
        }

        composeTestRule.onNodeWithText("Категория").assertIsDisplayed()
    }

    @Test
    fun testCreateButtonDisplayed() {
        composeTestRule.setContent {
            // Mock ExpensesAddScreen content
        }

        composeTestRule.onNodeWithText("Создать").assertIsDisplayed()
    }

    @Test
    fun testCancelButtonClickable() {
        composeTestRule.setContent {
            // Mock ExpensesAddScreen content
        }

        composeTestRule.onNodeWithText("Отмена").performClick()
    }
} 