package com.example.account

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AccountScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testAccountScreenTitleDisplayed() {
        composeTestRule.setContent {
            // Mock AccountScreen content
        }

        composeTestRule.onNodeWithText("Мой счет").assertIsDisplayed()
    }

    @Test
    fun testEditAccountButtonClickable() {
        composeTestRule.setContent {
            // Mock AccountScreen content
        }

        composeTestRule.onNodeWithText("Редактировать счет").performClick()
    }

    @Test
    fun testAccountNameDisplayed() {
        composeTestRule.setContent {
            // Mock AccountScreen content
        }

        composeTestRule.onNodeWithText("Название счета").assertIsDisplayed()
    }

    @Test
    fun testAccountBalanceDisplayed() {
        composeTestRule.setContent {
            // Mock AccountScreen content
        }

        composeTestRule.onNodeWithText("Баланс").assertIsDisplayed()
    }

    @Test
    fun testAccountCurrencyDisplayed() {
        composeTestRule.setContent {
            // Mock AccountScreen content
        }

        composeTestRule.onNodeWithText("Валюта").assertIsDisplayed()
    }
} 