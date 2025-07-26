package com.example.incomes

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IncomesHistoryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIncomesHistoryTitleDisplayed() {
        composeTestRule.setContent {
            // Mock IncomesHistoryScreen content
        }

        composeTestRule.onNodeWithText("История доходов").assertIsDisplayed()
    }

    @Test
    fun testAnalysisButtonClickable() {
        composeTestRule.setContent {
            // Mock IncomesHistoryScreen content
        }

        composeTestRule.onNodeWithText("Анализ").performClick()
    }

    @Test
    fun testDateRangeSelectorDisplayed() {
        composeTestRule.setContent {
            // Mock IncomesHistoryScreen content
        }

        composeTestRule.onNodeWithText("Период").assertIsDisplayed()
    }

    @Test
    fun testTotalAmountDisplayed() {
        composeTestRule.setContent {
            // Mock IncomesHistoryScreen content
        }

        composeTestRule.onNodeWithText("Сумма").assertIsDisplayed()
    }

    @Test
    fun testBackButtonClickable() {
        composeTestRule.setContent {
            // Mock IncomesHistoryScreen content
        }

        composeTestRule.onNodeWithText("Назад").performClick()
    }
} 