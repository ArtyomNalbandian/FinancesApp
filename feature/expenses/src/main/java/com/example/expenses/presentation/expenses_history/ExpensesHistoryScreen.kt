package com.example.expenses.presentation.expenses_history

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.account.di.DaggerAccountComponent
import com.example.account.presentation.AccountViewModel
import com.example.common.util.toCurrencySymbol
import com.example.database.di.DaggerDatabaseComponent
import com.example.expenses.di.DaggerExpensesComponent
import com.example.network.di.DaggerNetworkComponent
import com.example.ui.FinancesDatePickerDialog
import com.example.ui.FinancesDateRangeSelector
import com.example.ui.FinancesTopBarConfig
import com.example.ui.ListItem
import com.example.ui.R
import com.example.expenses.R.string
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
internal fun ExpensesHistoryScreen(
    navigateBack: () -> Unit,
    navigateToAnalysis: () -> Unit,
) {

    val networkComponent = DaggerNetworkComponent.create()
    val databaseComponent = DaggerDatabaseComponent.builder()
        .context(LocalContext.current.applicationContext)
        .build()
    val expensesComponent = DaggerExpensesComponent
        .factory()
        .create(
            networkApi = networkComponent,
            databaseApi = databaseComponent
        )
    val accountComponent = DaggerAccountComponent
        .factory()
        .create(
            networkApi = networkComponent,
            databaseApi = databaseComponent
        )
    val accountViewModel: AccountViewModel =
        viewModel(factory = accountComponent.viewModelFactory())
    val expensesHistoryViewModel: ExpensesHistoryViewModel =
        viewModel(factory = expensesComponent.viewModelFactory())

    FinancesTopBarConfig(
        title = { Text(stringResource(string.expenses_history_title)) },
        navAction = {
            IconButton(onClick = navigateBack) {
                Icon(painterResource(R.drawable.ic_back), contentDescription = stringResource(string.back))
            }
        },
        actions = {
            IconButton(onClick = navigateToAnalysis) {
                Icon(
                    painterResource(R.drawable.ic_analysis),
                    contentDescription = stringResource(string.analysis)
                )
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
    val state by expensesHistoryViewModel.state.collectAsState()

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

        ExpensesHistoryContent(state = state, currency = currency)

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
                    expensesHistoryViewModel.handleIntent(
                        ExpensesHistoryIntent.LoadHistory(
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
private fun ExpensesHistoryContent(state: ExpensesHistoryState, currency: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.tertiary)
    ) {
        when (state) {
            is ExpensesHistoryState.Loading -> {
                CircularProgressIndicator()
            }

            is ExpensesHistoryState.Error -> {
                Text(
                    state.message,
                    modifier = Modifier.padding(16.dp, top = 64.dp)
                )
            }

            is ExpensesHistoryState.Content -> {
                Column {
                    ListItem(
                        title = stringResource(string.amount),
                        amount = state.total,
                        currency = currency,
                        modifier = Modifier.height(56.dp),
                        backgroundColor = MaterialTheme.colorScheme.secondary,
                    )

                    if (state.items.isEmpty()) {
                        Text(
                            stringResource(string.no_operations),
                            modifier = Modifier.padding(16.dp),
                            color = Color.Gray
                        )
                    } else {
                        state.items.sortedBy { Instant.parse(it.transactionDate) }
                            .forEach { expense ->
                                ListItem(
                                    title = expense.title,
                                    leadingIcon = expense.leadingIcon,
                                    trailingIcon = R.drawable.more_vert,
                                    amount = expense.amount,
                                    currency = currency,
                                    supportingText = expense.subtitle,
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
