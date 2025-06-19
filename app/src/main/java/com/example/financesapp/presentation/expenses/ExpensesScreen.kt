package com.example.financesapp.presentation.expenses

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financesapp.data.remote.RetrofitInstance
import com.example.financesapp.data.remote.repository.RemoteDataSourceImpl
import com.example.financesapp.domain.usecase.impl.GetExpensesUseCaseImpl
import com.example.financesapp.presentation.common.AddButton
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ExpensesScreen(
    onNavigateToHistory: () -> Unit,
    onExpenseClick: () -> Unit,
    onCreateExpense: () -> Unit,
    accountId: Int?
) {
    val repository = remember { RemoteDataSourceImpl(RetrofitInstance.api) }
    val usecase = remember { GetExpensesUseCaseImpl(repository) }
    val viewModel: ExpensesViewModel = viewModel(
        factory = ExpensesViewModelFactory(usecase)
    )

    val state by viewModel.state.collectAsState()

    LaunchedEffect(accountId) {
        Log.d("testLog", "lE --- $accountId")
        if (accountId != null) {
            val today = LocalDate.now()
            val formattedDate = today.format(DateTimeFormatter.ISO_DATE)
            viewModel.handleIntent(
                ExpensesIntent.LoadExpenses(
                    accountId,
                    formattedDate,
                    formattedDate
                )
            )
        }
        viewModel.events.collect { event ->
            when (event) {
                is ExpensesEvent.NavigateToEditExpenseScreen -> onExpenseClick()
                is ExpensesEvent.NavigateToExpensesHistoryScreen -> onNavigateToHistory()
                is ExpensesEvent.NavigateToCreateExpenseScreen -> onCreateExpense()
                is ExpensesEvent.ShowError -> {}
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.tertiary)
    ) {

        when (val currentState = state) {
            is ExpensesState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is ExpensesState.Error -> {
                Text(
                    text = currentState.message,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }

            is ExpensesState.Content -> {
                ExpensesScreenContent(
                    expenses = currentState.expenses,
                    amount = currentState.total,
                    currency = currentState.currency,
                    onExpenseClick = { expenseId ->
                        viewModel.handleIntent(
                            ExpensesIntent.EditExpense(
                                accountId = 1,
                                expenseId = expenseId
                            )
                        )
                    }
                )
                AddButton(
                    onClick = { viewModel.handleIntent(ExpensesIntent.CreateExpense) },
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}
