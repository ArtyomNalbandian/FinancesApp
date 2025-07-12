package com.example.ui

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancesDatePickerDialog(
    pickerTarget: String?,
    startDate: LocalDate,
    endDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val initialDateMillis = when (pickerTarget) {
        "start" -> startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        "end" -> endDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        else -> now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialDateMillis)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { millis ->
                    val picked = Instant.ofEpochMilli(millis)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                    onDateSelected(picked)
                }
            }) { Text("Ок") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Отмена") }
        }
    ) {
        DatePicker(state = datePickerState, showModeToggle = false)
    }
}
