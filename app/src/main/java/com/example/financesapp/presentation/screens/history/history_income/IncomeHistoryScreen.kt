package com.example.financesapp.presentation.screens.history.history_income

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financesapp.R
import com.example.financesapp.data.remote.RetrofitInstance
import com.example.financesapp.data.remote.repository.AccountRepositoryImpl
import com.example.financesapp.data.remote.repository.TransactionRepositoryImpl
import com.example.financesapp.domain.usecases.impl.GetAccountsUseCaseImpl
import com.example.financesapp.domain.usecases.impl.GetIncomesUseCaseImpl
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.presentation.screens.history.HistoryIntent
import com.example.financesapp.utils.toCurrencySymbol
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeHistoryScreen() {

    val context = LocalContext.current

    val accountRepository = remember { AccountRepositoryImpl(RetrofitInstance.api) }
    val getAccountsUseCase = remember { GetAccountsUseCaseImpl(accountRepository) }
    val repository =
        remember { TransactionRepositoryImpl(RetrofitInstance.api, getAccountsUseCase) }
    val usecase = remember { GetIncomesUseCaseImpl(repository) }
    val viewModel: IncomeHistoryViewModel =
        viewModel(factory = IncomeHistoryViewModelFactory(usecase))

    var startDate by rememberSaveable { mutableStateOf(LocalDate.now().withDayOfMonth(1)) }
    var endDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var pickerTarget by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", Locale("ru"))

    val state by viewModel.state.collectAsState()

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
            when (val currentState = state) {
                is IncomeHistoryState.Loading -> {
                    CircularProgressIndicator()
                }

                is IncomeHistoryState.Error -> {
                    Text(
                        currentState.message,
                        modifier = Modifier.padding(16.dp, top = 64.dp)
                    )
                }

                is IncomeHistoryState.Content -> {
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
                            currentState.items.sortedBy { Instant.parse(it.transactionDate) }
                                .forEach { income ->
                                    ListItem(
                                        title = income.title,
                                        leadingIconStr = income.leadingIcon,
                                        trailingIcon = R.drawable.more_vert,
                                        amount = income.amount,
                                        currency = income.currency.toCurrencySymbol(),
                                        supportingText = income.subtitle,
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
