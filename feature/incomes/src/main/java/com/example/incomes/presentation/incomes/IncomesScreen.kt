package com.example.incomes.presentation.incomes

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
import com.example.database.di.DaggerDatabaseComponent
import com.example.incomes.R
import com.example.incomes.di.DaggerIncomesComponent
import com.example.network.di.DaggerNetworkComponent
import com.example.ui.AddButton
import com.example.ui.FinancesTopBarConfig

@Composable
internal fun IncomesScreen(
    navigateToHistory: () -> Unit,
    navigateToAddIncome: () -> Unit,
    navigateToEditIncome: (Int) -> Unit
) {

    val networkComponent = DaggerNetworkComponent.create()
    val databaseComponent = DaggerDatabaseComponent.builder()
        .context(LocalContext.current.applicationContext)
        .build()
    val incomesComponent = DaggerIncomesComponent
        .factory()
        .create(
            networkApi = networkComponent,
            databaseApi = databaseComponent
        )
    val accountComponent = DaggerAccountComponent.factory().create(networkApi = networkComponent)
    val accountViewModel: AccountViewModel =
        viewModel(factory = accountComponent.viewModelFactory())
    val incomesViewModel: IncomesViewModel =
        viewModel(factory = incomesComponent.viewModelFactory())
    Log.d("testLog", "$incomesComponent")

    FinancesTopBarConfig(
        title = { Text("Доходы сегодня") },
        actions = {
            IconButton(onClick = navigateToHistory) {
                Icon(painterResource(R.drawable.ic_history), contentDescription = "История")
            }
        }
    )

    val context = LocalContext.current
    val state by incomesViewModel.state.collectAsState()
    val account by accountViewModel.selectedAccount.collectAsStateWithLifecycle()
    val currency = account?.currency?.toCurrencySymbol().orEmpty()

    LaunchedEffect(Unit) {
        accountViewModel.loadAccount()
        incomesViewModel.loadIncome()
        incomesViewModel.event.collect { event ->
            when (event) {
                is IncomesEvent.ShowError -> {
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
        when (val currentState = state) {
            is IncomesState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is IncomesState.Error -> {
                val error = currentState.message
                Text("Ошибка: $error", color = Color.Red)
            }

            is IncomesState.Content -> {
                IncomesScreenContent(
                    income = currentState.income,
                    amount = currentState.total,
                    currency = currency,
                    onIncomeClick = {
                        navigateToEditIncome(it)
                    }
                )
                AddButton(
                    onClick = {
                        navigateToAddIncome()
                    },
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}
