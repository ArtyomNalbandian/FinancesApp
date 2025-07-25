package com.example.settings

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ColorPickerScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testColorPickerScreen() {
        composeTestRule.setContent {
            ColorPickerScreen(
                navigateBack = {}
            )
        }

        composeTestRule.onNodeWithText("Основной цвет").assertIsDisplayed()
        composeTestRule.onNodeWithText("Выберите цветовую схему").assertIsDisplayed()

        composeTestRule.onNodeWithText("Зеленая").assertIsDisplayed()
        composeTestRule.onNodeWithText("Синяя").assertIsDisplayed()
        composeTestRule.onNodeWithText("Оранжевая").assertIsDisplayed()
    }

    @Test
    fun testColorPaletteSelection() {
        composeTestRule.setContent {
            ColorPickerScreen(
                navigateBack = {}
            )
        }

        composeTestRule.onNodeWithText("Синяя").performClick()

        composeTestRule.onNodeWithText("Синяя").assertIsDisplayed()
    }
}
