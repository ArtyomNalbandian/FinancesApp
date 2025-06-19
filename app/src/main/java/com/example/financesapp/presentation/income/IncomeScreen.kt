package com.example.financesapp.presentation.income

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financesapp.data.mock.income
import com.example.financesapp.data.mock.incomeTotal
import com.example.financesapp.data.remote.RetrofitInstance
import com.example.financesapp.data.remote.repository.RemoteDataSourceImpl
import com.example.financesapp.domain.usecase.impl.GetExpensesUseCaseImpl
import com.example.financesapp.domain.usecase.impl.GetIncomesUseCaseImpl
import com.example.financesapp.presentation.common.AddButton
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.presentation.expenses.ExpensesEvent
import com.example.financesapp.presentation.expenses.ExpensesIntent
import com.example.financesapp.presentation.expenses.ExpensesState
import com.example.financesapp.presentation.expenses.ExpensesViewModel
import com.example.financesapp.presentation.expenses.ExpensesViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun IncomeScreen(
    onNavigateToHistory: () -> Unit,
    onIncomeClick: () -> Unit,
    onCreateIncome: () -> Unit,
    accountId: Int?
) {
    val repository = remember { RemoteDataSourceImpl(RetrofitInstance.api) }
    val usecase = remember { GetIncomesUseCaseImpl(repository) }
    val viewModel: IncomeViewModel = viewModel(
        factory = IncomeViewModelFactory(usecase)
    )

    val state by viewModel.state.collectAsState()

    LaunchedEffect(accountId) {
        Log.d("testLog", "LE --- $accountId")
        if (accountId != null) {
            val today = LocalDate.now()
            val formattedDate = today.format(DateTimeFormatter.ISO_DATE)
            viewModel.handleIntent(
                IncomeIntent.LoadIncome(
                    accountId,
                    formattedDate,
                    formattedDate
                )
            )
        }
        viewModel.events.collect { event ->
            when (event) {
                is IncomeEvent.NavigateToEditIncomeScreen -> onIncomeClick()
                is IncomeEvent.NavigateToIncomeHistoryScreen -> onNavigateToHistory()
                is IncomeEvent.NavigateToCreateIncomeScreen -> onCreateIncome()
                is IncomeEvent.ShowError -> {}
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.tertiary)
    ) {

        when (val currentState = state) {
            is IncomeState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is IncomeState.Error -> {
                Text(
                    text = currentState.message,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }

            is IncomeState.Content -> {
                IncomeScreenContent(
                    income = currentState.income,
                    amount = currentState.total,
                    currency = currentState.currency,
                    onExpenseClick = { incomeId ->
                        viewModel.handleIntent(
                            IncomeIntent.EditIncome(
                                accountId = 1,
                                incomeId = incomeId
                            )
                        )
                    }
                )
                AddButton(
                    onClick = { viewModel.handleIntent(IncomeIntent.CreateIncome) },
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}


//@Composable
//fun IncomeScreen() {
//    Box(modifier = Modifier.fillMaxSize()) {
//        Column {
//            ListItem(
//                title = incomeTotal.title,
//                amount = incomeTotal.amount,
//                backgroundColor = MaterialTheme.colorScheme.secondary,
//                modifier = Modifier.height(56.dp)
//            )
//            HorizontalDivider()
//            LazyColumn {
//                items(income) { income ->
//                    ListItem(
//                        title = income.title,
//                        trailingIcon = income.trailingIcon,
//                        amount = income.amount,
//                        onClick = { } //TODO
//                    )
//                    HorizontalDivider()
//                }
//            }
//        }
//    }
//}