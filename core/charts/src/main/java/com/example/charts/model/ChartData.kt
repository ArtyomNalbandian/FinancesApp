package com.example.charts.model

import androidx.compose.ui.graphics.Color

/**
 * Модель данных для кругового графика
 * @property label Название категории
 * @property value Значение (сумма)
 * @property percentage Процент от общей суммы
 * @property color Цвет сегмента
 * @property icon Иконка категории (emoji)
 */
data class PieChartData(
    val label: String,
    val value: Double,
    val percentage: Double,
    val color: Color,
    val icon: String? = null
)

/**
 * Модель данных для линейного графика
 * @property label Подпись точки
 * @property value Значение
 * @property color Цвет линии
 */
data class LineChartData(
    val label: String,
    val value: Double,
    val color: Color
)

/**
 * Модель данных для столбчатого графика
 * @property label Подпись столбца
 * @property value Значение
 * @property color Цвет столбца
 */
data class BarChartData(
    val label: String,
    val value: Double,
    val color: Color
)

/**
 * Конфигурация графика
 * @property title Заголовок графика
 * @property showLegend Показывать ли легенду
 * @property showValues Показывать ли значения
 * @property animationDuration Длительность анимации в миллисекундах
 */
data class ChartConfig(
    val title: String? = null,
    val showLegend: Boolean = true,
    val showValues: Boolean = true,
    val animationDuration: Long = 1000L
)
