# Charts Module

Модуль для отображения графиков в приложении FinancesApp.

## Описание

Модуль `core:charts` предоставляет независимые компоненты для отображения различных типов графиков. Модуль полностью изолирован от бизнес-логики приложения и получает данные через универсальные модели.

## Функциональность

### Круговой график (PieChart)

- ✅ Анимация появления
- ✅ Интерактивность (клики по сегментам)
- ✅ Отображение процентов на сегментах
- ✅ Легенда с возможностью выбора
- ✅ Поддержка иконок категорий
- ✅ Настраиваемые цвета

### Планируемые графики

- 📊 Линейный график (LineChart)
- 📊 Столбчатый график (BarChart)

## Архитектура

### Модели данных

```kotlin
// Универсальная модель для кругового графика
data class PieChartData(
    val label: String,
    val value: Double,
    val percentage: Double,
    val color: Color,
    val icon: String? = null
)

// Конфигурация графика
data class ChartConfig(
    val title: String? = null,
    val showLegend: Boolean = true,
    val showValues: Boolean = true,
    val animationDuration: Long = 1000L
)
```

### Утилиты

```kotlin
// Преобразование данных
object ChartDataMapper {
    fun mapToPieChartData(
        transactions: List<TransactionData>,
        colors: List<Color> = defaultColors
    ): List<PieChartData>
}
```

## Использование

### Базовое использование

```kotlin
@Composable
fun MyScreen() {
    val chartData = listOf(
        PieChartData(
            label = "Продукты",
            value = 1000.0,
            percentage = 50.0,
            color = Color.Blue,
            icon = "🛒"
        )
    )
    
    PieChart(
        data = chartData,
        config = ChartConfig(title = "Расходы по категориям")
    )
}
```

### С интерактивностью

```kotlin
PieChart(
    data = chartData,
    onSegmentClick = { segment ->
        // Обработка клика по сегменту
        println("Выбрана категория: ${segment.label}")
    }
)
```

### Преобразование данных из приложения

```kotlin
val transactions = listOf(
    TransactionData("Продукты", "1000", "🛒"),
    TransactionData("Транспорт", "500", "🚗")
)

val pieChartData = ChartDataMapper.mapToPieChartData(transactions)
```

## Зависимости

Модуль использует только базовые зависимости Compose:
- `androidx.compose.ui`
- `androidx.compose.material3`
- `androidx.compose.animation`

**НЕ использует:**
- Retrofit
- Room
- Dagger
- Другие сетевые или базовые библиотеки

## Тестирование

Модуль включает UI тесты для проверки:
- Отображения графика
- Интерактивности
- Обработки пустых данных
- Преобразования данных

## Интеграция

Модуль интегрирован в:
- Экран анализа расходов (`ExpensesAnalysisScreen`)
- Экран анализа доходов (`IncomesAnalysisScreen`)

Графики отображаются после выбора периода и перед списком транзакций. 