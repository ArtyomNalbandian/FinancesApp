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
class IncomesAnalysisScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIncomesAnalysisTitleDisplayed() {
        composeTestRule.setContent {
            // Mock IncomesAnalysisScreen content
        }

        composeTestRule.onNodeWithText("Анализ доходов").assertIsDisplayed()
    }

    @Test
    fun testDateRangeSelectorDisplayed() {
        composeTestRule.setContent {
            // Mock IncomesAnalysisScreen content
        }

        composeTestRule.onNodeWithText("Период").assertIsDisplayed()
    }

    @Test
    fun testTotalAmountDisplayed() {
        composeTestRule.setContent {
            // Mock IncomesAnalysisScreen content
        }

        composeTestRule.onNodeWithText("Сумма").assertIsDisplayed()
    }

    @Test
    fun testBackButtonClickable() {
        composeTestRule.setContent {
            // Mock IncomesAnalysisScreen content
        }

        composeTestRule.onNodeWithText("Назад").performClick()
    }

    @Test
    fun testNoOperationsMessageDisplayed() {
        composeTestRule.setContent {
            // Mock IncomesAnalysisScreen content
        }

        composeTestRule.onNodeWithText("Нет операций").assertIsDisplayed()
    }
} 