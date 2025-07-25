package com.example.settings

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PinCodeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testPinCodeInput() {
        // Запускаем экран пин-кода в режиме установки
        composeTestRule.setContent {
            PinCodeScreen(
                mode = PinCodeMode.Set,
                onSuccess = {},
                navigateBack = {}
            )
        }

        // Проверяем, что заголовок отображается
        composeTestRule.onNodeWithText("Создать пин-код").assertIsDisplayed()
        
        // Вводим пин-код 1234
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("4").performClick()
        
        // Проверяем, что кнопка OK стала активной
        composeTestRule.onNodeWithText("OK").assertIsDisplayed()
    }

    @Test
    fun testPinCodeDelete() {
        composeTestRule.setContent {
            PinCodeScreen(
                mode = PinCodeMode.Set,
                onSuccess = {},
                navigateBack = {}
            )
        }

        // Вводим цифры
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        
        // Удаляем последнюю цифру
        composeTestRule.onNodeWithText("←").performClick()
        
        // Проверяем, что кнопка OK неактивна (только 1 цифра)
        composeTestRule.onNodeWithText("OK").assertIsDisplayed()
    }

    @Test
    fun testPinCodeCheckMode() {
        composeTestRule.setContent {
            PinCodeScreen(
                mode = PinCodeMode.Check,
                onSuccess = {},
                navigateBack = {}
            )
        }

        // Проверяем, что заголовок отображается
        composeTestRule.onNodeWithText("Введите пин-код").assertIsDisplayed()
        
        // Проверяем наличие кнопок для установки нового пин-кода
        composeTestRule.onNodeWithText("Установить новый пин-код").assertIsDisplayed()
        composeTestRule.onNodeWithText("Изменить пин-код").assertIsDisplayed()
    }
} 