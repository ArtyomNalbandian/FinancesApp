package com.example.ui

import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun FinancesDateRangeSelector(
    startDate: LocalDate,
    endDate: LocalDate,
    dateFormatter: DateTimeFormatter,
    onStartClick: () -> Unit,
    onEndClick: () -> Unit
) {
    ListItem(
        title = "Начало",
        amount = startDate.format(dateFormatter),
        backgroundColor = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.height(56.dp),
        onClick = onStartClick
    )
    HorizontalDivider()
    ListItem(
        title = "Конец",
        amount = endDate.format(dateFormatter),
        backgroundColor = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.height(56.dp),
        onClick = onEndClick
    )
    HorizontalDivider()
}
