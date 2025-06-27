package com.example.financesapp.presentation.expenses

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financesapp.data.remote.RetrofitInstance
import com.example.financesapp.data.remote.repository.AccountRepositoryImpl
import com.example.financesapp.data.remote.repository.RemoteDataSourceImpl
import com.example.financesapp.data.remote.repository.TransactionRepositoryImpl
import com.example.financesapp.domain.repositories.AccountRepository
import com.example.financesapp.domain.usecase.GetAccountsUseCase
import com.example.financesapp.domain.usecase.impl.GetAccountsUseCaseImpl
import com.example.financesapp.domain.usecase.impl.GetExpensesUseCaseImpl
import com.example.financesapp.presentation.common.AddButton
import com.example.financesapp.utils.NetworkMonitor


@Composable
fun ExpensesScreen() {

    val context = LocalContext.current
    val networkMonitor = remember { NetworkMonitor(context.applicationContext) }

    val accountRepository = remember { AccountRepositoryImpl(RetrofitInstance.api) }
    val getAccountsUseCase = remember { GetAccountsUseCaseImpl(accountRepository) }
    val repository = remember { TransactionRepositoryImpl(RetrofitInstance.api, getAccountsUseCase) }
    val usecase = remember { GetExpensesUseCaseImpl(repository) }
    val viewModel: ExpensesViewModel = viewModel(
        factory = ExpensesViewModelFactory(usecase, networkMonitor)
    )

    val state by viewModel.state.collectAsState()
    val isConnected by networkMonitor.isConnected.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is ExpensesEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val showOfflineBanner = !isConnected && state !is ExpensesState.Content

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
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
                    currency = content.currency,
                    onExpenseClick = {}
                )
                AddButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}
