package com.example.financesapp.presentation.expenses

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
import com.example.financesapp.R
import com.example.financesapp.data.remote.RetrofitInstance
import com.example.financesapp.data.remote.repository.RemoteDataSourceImpl
import com.example.financesapp.domain.usecase.impl.GetExpensesUseCaseImpl
import com.example.financesapp.presentation.common.AddButton
import com.example.financesapp.presentation.common.TopAppBarState
import com.example.financesapp.presentation.common.TopAppBarStateProvider

@Composable
fun ExpensesScreen(
    onNavigateToHistory: () -> Unit,
    onExpenseClick: () -> Unit,
    onCreateExpense: () -> Unit
) {
    val repository = remember { RemoteDataSourceImpl(RetrofitInstance.api) }
    val usecase = remember { GetExpensesUseCaseImpl(repository) }
    val viewModel: ExpensesViewModel = viewModel(
        factory = SpendingViewModelFactory(usecase)
    )

    TopAppBarStateProvider.update(
        TopAppBarState(
            title = "Расходы сегодня",
            trailingIcon = R.drawable.ic_history,
            onTrailingIconClick = { viewModel.handleIntent(ExpensesIntent.OpenHistoryOfExpenses(1)) }
        )
    )

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
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
            is ExpensesScreenState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is ExpensesScreenState.Error -> {
                Text(
                    text = currentState.message,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }

            is ExpensesScreenState.Content -> {
                ExpensesScreenContent(
                    expenses = currentState.expenses,
                    amount = currentState.total,
                    currency = currentState.currency,
                    onExpenseClick = { expenseId ->
                        viewModel.handleIntent(ExpensesIntent.EditExpense(accountId = 1, expenseId = expenseId))
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