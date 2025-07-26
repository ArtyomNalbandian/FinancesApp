package com.example.categories

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
class CategoriesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCategoriesScreenTitleDisplayed() {
        composeTestRule.setContent {
            // Mock CategoriesScreen content
        }

        composeTestRule.onNodeWithText("Мои категории").assertIsDisplayed()
    }

    @Test
    fun testSearchBarDisplayed() {
        composeTestRule.setContent {
            // Mock CategoriesScreen content
        }

        composeTestRule.onNodeWithText("Поиск").assertIsDisplayed()
    }

    @Test
    fun testSearchFunctionality() {
        composeTestRule.setContent {
            // Mock CategoriesScreen content
        }

        composeTestRule.onNodeWithText("Поиск").performTextInput("Продукты")
    }

    @Test
    fun testCategoriesListDisplayed() {
        composeTestRule.setContent {
            // Mock CategoriesScreen content
        }

        composeTestRule.onNodeWithText("Категории").assertIsDisplayed()
    }

    @Test
    fun testCategoryItemClickable() {
        composeTestRule.setContent {
            // Mock CategoriesScreen content
        }

        composeTestRule.onNodeWithText("Продукты").performClick()
    }
} 