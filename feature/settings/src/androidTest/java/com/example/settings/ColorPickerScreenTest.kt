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
    fun testColorPickerTitleDisplayed() {
        composeTestRule.setContent {
            // Mock ColorPickerScreen content
        }

        composeTestRule.onNodeWithText("Основной цвет").assertIsDisplayed()
    }

    @Test
    fun testChooseColorSchemeTextDisplayed() {
        composeTestRule.setContent {
            // Mock ColorPickerScreen content
        }

        composeTestRule.onNodeWithText("Выберите цветовую схему:").assertIsDisplayed()
    }

    @Test
    fun testGreenPaletteClickable() {
        composeTestRule.setContent {
            // Mock ColorPickerScreen content
        }

        composeTestRule.onNodeWithText("Зелёная").performClick()
    }

    @Test
    fun testBluePaletteClickable() {
        composeTestRule.setContent {
            // Mock ColorPickerScreen content
        }

        composeTestRule.onNodeWithText("Синяя").performClick()
    }

    @Test
    fun testBackButtonClickable() {
        composeTestRule.setContent {
            // Mock ColorPickerScreen content
        }

        composeTestRule.onNodeWithText("Назад").performClick()
    }
}
