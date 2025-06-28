package com.example.financesapp.presentation.history.history_expenses

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financesapp.R
import com.example.financesapp.data.remote.RetrofitInstance
import com.example.financesapp.data.remote.repository.RemoteDataSourceImpl
import com.example.financesapp.domain.usecase.impl.GetExpensesUseCaseImpl
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.presentation.history.HistoryIntent
import com.example.financesapp.presentation.income.IncomeEvent
import com.example.financesapp.presentation.income.IncomeState
import com.example.financesapp.utils.NetworkMonitor
import com.example.financesapp.utils.toCurrencySymbol
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesHistoryScreen() {

    val context = LocalContext.current
    val networkMonitor = remember { NetworkMonitor(context.applicationContext) }

    val repository = remember { RemoteDataSourceImpl(RetrofitInstance.api) }
    val usecase = remember { GetExpensesUseCaseImpl(repository) }
    val viewModel: ExpensesHistoryViewModel =
        viewModel(factory = ExpensesHistoryViewModelFactory(usecase, networkMonitor))

    var startDate by rememberSaveable { mutableStateOf(LocalDate.now().withDayOfMonth(1)) }
    var endDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var pickerTarget by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", Locale("ru"))

    val state by viewModel.state.collectAsState()
    val isConnected by networkMonitor.isConnected.collectAsState()

    val showOfflineBanner = !isConnected && state !is ExpensesHistoryState.Content

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ListItem(
            title = "Начало",
            amount = startDate.format(dateFormatter),
            backgroundColor = MaterialTheme.colorScheme.secondary,
            modifier = Modifier,
            onClick = {
                pickerTarget = "start"
                showDialog = true
            }
        )
        HorizontalDivider()
        ListItem(
            title = "Конец",
            amount = endDate.format(dateFormatter),
            backgroundColor = MaterialTheme.colorScheme.secondary,
            modifier = Modifier,
            onClick = {
                pickerTarget = "end"
                showDialog = true
            }
        )
        HorizontalDivider()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.tertiary)
        ) {
            if (showOfflineBanner) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Red)
                        .height(56.dp)
                        .padding(horizontal = 16.dp)
                        .align(Alignment.TopCenter),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Нет подключения к интернету",
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = { viewModel.retryLoad() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Обновить",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Обновить", color = Color.White)
                    }
                }
            }
            when (val currentState = state) {
                is ExpensesHistoryState.Loading -> {
                    CircularProgressIndicator()
                }

                is ExpensesHistoryState.Error -> {
                    Text(
                        currentState.message,
                        modifier = Modifier.padding(16.dp, top = 64.dp)
                    )
                }

                is ExpensesHistoryState.Content -> {
                    Column {
                        ListItem(
                            title = "Сумма",
                            amount = currentState.total,
                            currency = "",
                            modifier = Modifier,
                            backgroundColor = MaterialTheme.colorScheme.secondary,
                        )
                        if (currentState.items.isEmpty()) {
                            Text(
                                "Нет операций",
                                modifier = Modifier.padding(16.dp),
                                color = Color.Gray
                            )
                        } else {
                            val outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm", Locale("ru"))
                            currentState.items.sortedBy { Instant.parse(it.transactionDate) }.forEach { expense ->
                                val formattedDate = try {
                                    val instant = Instant.parse(expense.transactionDate)
                                    val dateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
                                    dateTime.format(outputFormatter)
                                } catch (e: Exception) {
                                    expense.transactionDate
                                }
                                ListItem(
                                    title = expense.title,
                                    leadingIconStr = expense.leadingIcon,
                                    trailingIcon = R.drawable.more_vert,
                                    amount = expense.amount,
                                    currency = expense.currency.toCurrencySymbol(),
                                    supportingText = expense.subtitle,
                                    subtitle = formattedDate,
                                    onClick = {}
                                )
                                HorizontalDivider()
                            }
                        }
                    }

                }
            }
        }
    }
    if (showDialog) {
        val initialDateMillis = when (pickerTarget) {
            "start" -> startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            "end" -> endDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            else -> now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        }
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialDateMillis)
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val picked = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        when (pickerTarget) {
                            "start" -> startDate = picked
                            "end" -> endDate = picked
                        }
                        viewModel.handleIntent(
                            HistoryIntent.LoadHistory(
                                startDate = startDate.toString(),
                                endDate = endDate.toString()
                            )
                        )
                    }
                    showDialog = false
                }) { Text("Ок") }

            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Отмена") }
            }
        ) {
            DatePicker(state = datePickerState, showModeToggle = false)
        }
    }
}
