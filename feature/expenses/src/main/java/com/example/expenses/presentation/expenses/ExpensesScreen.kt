package com.example.expenses.presentation.expenses

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.account.di.DaggerAccountComponent
import com.example.account.presentation.AccountViewModel
import com.example.common.util.toCurrencySymbol
import com.example.expenses.R
import com.example.expenses.di.DaggerExpensesComponent
import com.example.network.di.DaggerNetworkComponent
import com.example.ui.AddButton
import com.example.ui.FinancesTopBarConfig

@Composable
internal fun ExpensesScreen(
    navigateToHistory: () -> Unit
) {

    val networkComponent = DaggerNetworkComponent.create()
    val expensesComponent = DaggerExpensesComponent.factory().create(networkApi = networkComponent)
    val accountComponent = DaggerAccountComponent.factory().create(networkApi = networkComponent)
    val accountViewModel: AccountViewModel =
        viewModel(factory = accountComponent.viewModelFactory())
    val expensesViewModel: ExpensesViewModel =
        viewModel(factory = expensesComponent.viewModelFactory())
    Log.d("testLog", "$expensesComponent")

    FinancesTopBarConfig(
        title = { Text("Расходы сегодня") },
        actions = {
            IconButton(onClick = navigateToHistory) {
                Icon(painterResource(R.drawable.ic_history), contentDescription = "История")
            }
        }
    )

    val context = LocalContext.current
    val state by expensesViewModel.state.collectAsState()
    val account by accountViewModel.selectedAccount.collectAsStateWithLifecycle()
    val currency = account?.currency?.toCurrencySymbol().orEmpty()

    LaunchedEffect(Unit) {
        accountViewModel.loadAccount()
        expensesViewModel.loadExpenses()
        expensesViewModel.event.collect { event ->
            when (event) {
                is ExpensesEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        when (state) {
            is ExpensesState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is ExpensesState.Error -> {
                val error = (state as ExpensesState.Error).message
                Text("Ошибка: $error", color = Color.Red)
            }

            is ExpensesState.Content -> {
                val content = state as ExpensesState.Content
                ExpensesScreenContent(
                    expenses = content.expenses,
                    amount = content.total,
                    currency = currency,
                    onExpenseClick = {})
                AddButton(
                    onClick = {}, modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}
