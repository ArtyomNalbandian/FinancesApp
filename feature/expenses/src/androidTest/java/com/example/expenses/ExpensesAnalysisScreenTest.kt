package com.example.expenses

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExpensesAnalysisScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testExpensesAnalysisTitleDisplayed() {
        composeTestRule.setContent {
            // Mock ExpensesAnalysisScreen content
        }

        composeTestRule.onNodeWithText("Анализ расходов").assertIsDisplayed()
    }

    @Test
    fun testDateRangeSelectorDisplayed() {
        composeTestRule.setContent {
            // Mock ExpensesAnalysisScreen content
        }

        composeTestRule.onNodeWithText("Период").assertIsDisplayed()
    }

    @Test
    fun testTotalAmountDisplayed() {
        composeTestRule.setContent {
            // Mock ExpensesAnalysisScreen content
        }

        composeTestRule.onNodeWithText("Сумма").assertIsDisplayed()
    }

    @Test
    fun testBackButtonClickable() {
        composeTestRule.setContent {
            // Mock ExpensesAnalysisScreen content
        }

        composeTestRule.onNodeWithText("Назад").performClick()
    }

    @Test
    fun testNoOperationsMessageDisplayed() {
        composeTestRule.setContent {
            // Mock ExpensesAnalysisScreen content
        }

        composeTestRule.onNodeWithText("Нет операций").assertIsDisplayed()
    }
} 