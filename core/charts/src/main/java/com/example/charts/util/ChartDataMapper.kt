package com.example.charts.util

import androidx.compose.ui.graphics.Color
import com.example.charts.model.PieChartData

/**
 * Утилиты для преобразования данных в формат графиков
 */
object ChartDataMapper {

    /**
     * Преобразует список транзакций в данные для кругового графика
     * @param transactions Список транзакций
     * @param colors Список цветов для сегментов
     * @return Список данных для кругового графика
     */
    fun mapToPieChartData(
        transactions: List<TransactionData>,
        colors: List<Color> = defaultColors
    ): List<PieChartData> {
        if (transactions.isEmpty()) return emptyList()

        val groupedData = transactions.groupBy { it.categoryName }
            .mapValues { (_, transactions) ->
                transactions.sumOf { it.amount.toDoubleOrNull() ?: 0.0 }
            }

        val total = groupedData.values.sum()

        return groupedData.entries.mapIndexed { index, (categoryName, amount) ->
            val percentage = if (total > 0) (amount / total) * 100 else 0.0
            val color = colors.getOrElse(index % colors.size) { colors.first() }
            val icon = transactions.find { it.categoryName == categoryName }?.icon

            PieChartData(
                label = categoryName,
                value = amount,
                percentage = percentage,
                color = color,
                icon = icon
            )
        }.sortedByDescending { it.value }
    }

    /**
     * Стандартные цвета для графиков
     */
    private val defaultColors = listOf(
        Color(0xFF2196F3),
        Color(0xFF4CAF50),
        Color(0xFFFF9800),
        Color(0xFFE91E63),
        Color(0xFF9C27B0),
        Color(0xFF00BCD4),
        Color(0xFFFF5722),
        Color(0xFF795548),
        Color(0xFF607D8B),
        Color(0xFF3F51B5)
    )
}

/**
 * Универсальная модель данных для графиков
 * @property categoryName Название категории
 * @property amount Сумма
 * @property icon Иконка категории
 */
data class TransactionData(
    val categoryName: String,
    val amount: String,
    val icon: String? = null
)
