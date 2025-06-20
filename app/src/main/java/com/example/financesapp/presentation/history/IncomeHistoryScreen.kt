package com.example.financesapp.presentation.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.financesapp.R
import com.example.financesapp.data.remote.RetrofitInstance
import com.example.financesapp.data.remote.repository.RemoteDataSourceImpl
import com.example.financesapp.domain.usecase.impl.GetIncomesUseCaseImpl
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.utils.toCurrencySymbol
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeHistoryScreen(
    navController: NavHostController,
//    type: String,
//    accountId: Int?
) {
//    val isIncome = type == "доходы"

    val repository = remember { RemoteDataSourceImpl(RetrofitInstance.api) }
    val usecase = remember { GetIncomesUseCaseImpl(repository) }
    val viewModel: IncomeHistoryViewModel = viewModel(factory = IncomeHistoryViewModelFactory(usecase))

    var startDate by rememberSaveable { mutableStateOf(LocalDate.now().withDayOfMonth(1)) }
    var endDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var pickerTarget by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", Locale("ru"))


    LaunchedEffect(startDate, endDate) {
//        if(accountId != null) {
        viewModel.handleIntent(
            HistoryIntent.LoadHistory(
                startDate = startDate.toString(),
                endDate = endDate.toString()
            ),
//                isIncome = isIncome
        )
//        }
    }

    val uiState by viewModel.uiState.collectAsState()

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        ListItem(
            title = "Начало",
            amount = startDate.format(dateFormatter),
            backgroundColor = Color(0xFFD4FAE6),
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
            backgroundColor = Color(0xFFD4FAE6),
            modifier = Modifier,
            onClick = {
                pickerTarget = "end"
                showDialog = true
            }
        )
        HorizontalDivider()

        when (val state = uiState) {
            is IncomeHistoryState.Loading -> {
                Box(Modifier.fillMaxWidth().height(120.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is IncomeHistoryState.Success -> {

                ListItem(
                    title = "Сумма",
                    amount = state.total,
                    currency = "",
                    modifier = Modifier,
                    backgroundColor = Color(0xFFD4FAE6),
                )

                if (state.items.isEmpty()) {
                    Text("Нет операций", modifier = Modifier.padding(16.dp), color = Color.Gray)
                } else {
                    state.items.forEach { income ->
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
            is IncomeHistoryState.Error -> {
                Text(
                    state.message,
                    modifier = Modifier.padding(16.dp)
                )
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
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {
                        val picked = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
                        when (pickerTarget) {
                            "start" -> startDate = picked
                            "end" -> endDate = picked
                        }
                    }
                    showDialog = false
                }) {
                    Text("Ок")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Отмена") }
            }
        ) {
            DatePicker(state = datePickerState, showModeToggle = false)
        }
    }
}