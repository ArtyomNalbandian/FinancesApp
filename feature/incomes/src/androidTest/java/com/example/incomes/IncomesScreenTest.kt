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
class IncomesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIncomesScreenTitleDisplayed() {
        composeTestRule.setContent {
            // Mock IncomesScreen content
        }

        composeTestRule.onNodeWithText("Доходы за сегодня").assertIsDisplayed()
    }

    @Test
    fun testHistoryButtonClickable() {
        composeTestRule.setContent {
            // Mock IncomesScreen content
        }

        composeTestRule.onNodeWithText("История").performClick()
    }

    @Test
    fun testAddIncomeButtonDisplayed() {
        composeTestRule.setContent {
            // Mock IncomesScreen content
        }

        composeTestRule.onNodeWithText("+").assertIsDisplayed()
    }

    @Test
    fun testTotalIncomeDisplayed() {
        composeTestRule.setContent {
            // Mock IncomesScreen content
        }

        composeTestRule.onNodeWithText("Итого").assertIsDisplayed()
    }

    @Test
    fun testIncomesListDisplayed() {
        composeTestRule.setContent {
            // Mock IncomesScreen content
        }

        composeTestRule.onNodeWithText("Доходы").assertIsDisplayed()
    }
} 