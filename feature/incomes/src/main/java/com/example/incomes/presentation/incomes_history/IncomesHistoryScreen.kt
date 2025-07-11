package com.example.incomes.presentation.incomes_history

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.account.di.DaggerAccountComponent
import com.example.account.presentation.AccountViewModel
import com.example.common.util.toCurrencySymbol
import com.example.incomes.di.DaggerIncomesComponent
import com.example.network.di.DaggerNetworkComponent
import com.example.ui.FinancesDatePickerDialog
import com.example.ui.FinancesDateRangeSelector
import com.example.ui.FinancesTopBarConfig
import com.example.ui.ListItem
import com.example.ui.R
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun IncomesHistoryScreen(
    navigateBack: () -> Unit,
    navigateToAnalysis: () -> Unit,
) {

    val networkComponent = DaggerNetworkComponent.create()
    val incomesComponent = DaggerIncomesComponent.factory().create(networkApi = networkComponent)
    val accountComponent = DaggerAccountComponent.factory().create(networkApi = networkComponent)
    val accountViewModel: AccountViewModel =
        viewModel(factory = accountComponent.viewModelFactory())
    val incomesHistoryViewModel: IncomesHistoryViewModel =
        viewModel(factory = incomesComponent.viewModelFactory())
    Log.d("testLog", "$incomesComponent")

    FinancesTopBarConfig(
        title = { Text("История доходов") },
        navAction = {
            IconButton(onClick = navigateBack) {
                Icon(painterResource(R.drawable.ic_back), contentDescription = "Назад")
            }
        },
        actions = {
            IconButton(onClick = navigateToAnalysis) {
                Icon(painterResource(R.drawable.ic_analysis), contentDescription = "Анализ доходов")
            }
        }
    )

    val account by accountViewModel.selectedAccount.collectAsStateWithLifecycle()
    val currency = account?.currency?.toCurrencySymbol().orEmpty()

    LaunchedEffect(Unit) {
        accountViewModel.loadAccount()
    }

    var startDate by rememberSaveable { mutableStateOf(LocalDate.now().withDayOfMonth(1)) }
    var endDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var pickerTarget by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", Locale("ru"))
    val state by incomesHistoryViewModel.state.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        FinancesDateRangeSelector(
            startDate = startDate,
            endDate = endDate,
            dateFormatter = dateFormatter,
            onStartClick = {
                pickerTarget = "start"
                showDialog = true
            },
            onEndClick = {
                pickerTarget = "end"
                showDialog = true
            }
        )

        IncomesHistoryContent(state = state, currency = currency)

        if (showDialog) {
            FinancesDatePickerDialog(
                pickerTarget = pickerTarget,
                startDate = startDate,
                endDate = endDate,
                onDateSelected = { selectedDate ->
                    when (pickerTarget) {
                        "start" -> startDate = selectedDate
                        "end" -> endDate = selectedDate
                    }
                    incomesHistoryViewModel.handleIntent(
                        IncomesHistoryIntent.LoadHistory(
                            startDate = startDate.toString(),
                            endDate = endDate.toString()
                        )
                    )
                    showDialog = false
                },
                onDismiss = { showDialog = false }
            )
        }
    }
}

@Composable
private fun IncomesHistoryContent(state: IncomesHistoryState, currency: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.tertiary)
    ) {
        when (state) {
            is IncomesHistoryState.Loading -> {
                CircularProgressIndicator()
            }

            is IncomesHistoryState.Error -> {
                Text(
                    state.message,
                    modifier = Modifier.padding(16.dp, top = 64.dp)
                )
            }

            is IncomesHistoryState.Content -> {
                Column {
                    ListItem(
                        title = "Сумма",
                        amount = state.total,
                        currency = currency,
                        modifier = Modifier.height(56.dp),
                        backgroundColor = MaterialTheme.colorScheme.secondary,
                    )

                    if (state.items.isEmpty()) {
                        Text(
                            "Нет операций",
                            modifier = Modifier.padding(16.dp),
                            color = Color.Gray
                        )
                    } else {
                        state.items.sortedBy { Instant.parse(it.transactionDate) }
                            .forEach { income ->
                                ListItem(
                                    title = income.title,
                                    leadingIcon = income.leadingIcon,
                                    trailingIcon = R.drawable.more_settings,
                                    amount = income.amount,
                                    currency = currency,
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
