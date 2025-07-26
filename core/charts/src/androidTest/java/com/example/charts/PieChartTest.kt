package com.example.charts

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.charts.model.ChartConfig
import com.example.charts.model.PieChartData
import com.example.charts.util.ChartDataMapper
import com.example.charts.util.TransactionData
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.compose.ui.graphics.Color

@RunWith(AndroidJUnit4::class)
class PieChartTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testPieChartDisplayed() {
        val testData = listOf(
            PieChartData(
                label = "Продукты",
                value = 1000.0,
                percentage = 50.0,
                color = Color.Blue,
                icon = "🛒"
            ),
            PieChartData(
                label = "Транспорт",
                value = 500.0,
                percentage = 25.0,
                color = Color.Green,
                icon = "🚗"
            ),
            PieChartData(
                label = "Развлечения",
                value = 500.0,
                percentage = 25.0,
                color = Color.Red,
                icon = "🎮"
            )
        )

        composeTestRule.setContent {
            PieChart(
                data = testData,
                config = ChartConfig(title = "Расходы по категориям")
            )
        }

        composeTestRule.onNodeWithText("Расходы по категориям").assertIsDisplayed()
        composeTestRule.onNodeWithText("Продукты").assertIsDisplayed()
        composeTestRule.onNodeWithText("Транспорт").assertIsDisplayed()
        composeTestRule.onNodeWithText("Развлечения").assertIsDisplayed()
    }

    @Test
    fun testPieChartWithEmptyData() {
        composeTestRule.setContent {
            PieChart(data = emptyList())
        }

        // График не должен отображаться при пустых данных
        // Тест проходит, если нет ошибок
    }

    @Test
    fun testPieChartLegendInteraction() {
        val testData = listOf(
            PieChartData(
                label = "Продукты",
                value = 1000.0,
                percentage = 100.0,
                color = Color.Blue,
                icon = "🛒"
            )
        )

        composeTestRule.setContent {
            PieChart(
                data = testData,
                onSegmentClick = { /* callback */ }
            )
        }

        composeTestRule.onNodeWithText("Продукты").performClick()
    }

    @Test
    fun testChartDataMapper() {
        val transactions = listOf(
            TransactionData("Продукты", "1000", "🛒"),
            TransactionData("Транспорт", "500", "🚗"),
            TransactionData("Продукты", "500", "🛒") // Дублирующая категория
        )

        val pieChartData = ChartDataMapper.mapToPieChartData(transactions)

        assert(pieChartData.size == 2) // Должно быть 2 уникальные категории
        assert(pieChartData.first().label == "Продукты") // Продукты должны быть первыми (большая сумма)
        assert(pieChartData.first().value == 1500.0) // Сумма должна быть объединена
    }
} 