package com.example.expenses

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.expenses.presentation.expenses.ExpensesScreen

@RunWith(AndroidJUnit4::class)
class ExpensesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testExpensesScreenTitleDisplayed() {
        composeTestRule.setContent {
            ExpensesScreen(
                navigateToHistory = {},
                navigateToAddExpense = {},
                navigateToEditExpense = {}
            )
        }

        composeTestRule.onNodeWithText("Расходы за сегодня").assertIsDisplayed()
    }

    @Test
    fun testHistoryButtonClickable() {
        composeTestRule.setContent {
            ExpensesScreen(
                navigateToHistory = {},
                navigateToAddExpense = {},
                navigateToEditExpense = {}
            )
        }

        composeTestRule.onNodeWithText("История").performClick()
    }

    @Test
    fun testAddExpenseButtonDisplayed() {
        composeTestRule.setContent {
            ExpensesScreen(
                navigateToHistory = {},
                navigateToAddExpense = {},
                navigateToEditExpense = {}
            )
        }

        composeTestRule.onNodeWithText("+").assertIsDisplayed()
    }

    @Test
    fun testTotalAmountDisplayed() {
        composeTestRule.setContent {
            ExpensesScreen(
                navigateToHistory = {},
                navigateToAddExpense = {},
                navigateToEditExpense = {}
            )
        }

        composeTestRule.onNodeWithText("Итого").assertIsDisplayed()
    }

    @Test
    fun testExpensesListDisplayed() {
        composeTestRule.setContent {
            ExpensesScreen(
                navigateToHistory = {},
                navigateToAddExpense = {},
                navigateToEditExpense = {}
            )
        }

        composeTestRule.onNodeWithText("Расходы").assertIsDisplayed()
    }
} 