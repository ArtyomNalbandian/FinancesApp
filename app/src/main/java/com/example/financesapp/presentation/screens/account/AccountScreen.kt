package com.example.financesapp.presentation.screens.account

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financesapp.data.remote.RetrofitInstance
import com.example.financesapp.data.remote.repository.AccountRepositoryImpl
import com.example.financesapp.domain.usecases.impl.GetAccountsUseCaseImpl
import com.example.financesapp.presentation.common.AddButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen() {

    val context = LocalContext.current

    val repository = remember { AccountRepositoryImpl(RetrofitInstance.api) }
    val usecase = remember { GetAccountsUseCaseImpl(repository) }
    val viewModel: AccountViewModel = viewModel(
        factory = AccountViewModelFactory(usecase)
    )

    val sheetState = rememberModalBottomSheetState()

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is AccountEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val currentState = state) {
            is AccountState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is AccountState.Error -> {
                val error = currentState.message
                Text("Ошибка: $error", modifier = Modifier.align(Alignment.Center))
            }

            is AccountState.Content -> {
                AccountScreenContent(
                    account = currentState.account,
                    onCurrencySelectorClick = {
                        viewModel.handleIntent(
                            AccountIntent.ShowCurrencySelector(
                                currentState.account.id
                            )
                        )
                    }
                )
                AddButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
                if (currentState.isCurrencySelectorVisible) {
                    AccountBottomSheet(
                        sheetState = sheetState,
                        onDismissRequest = {
                            viewModel.handleIntent(AccountIntent.HideCurrencySelector)
                        },
                        onCurrencySelected = { selectedCurrency ->
                            viewModel.handleIntent(
                                AccountIntent.ChangeCurrency(
                                    accountId = currentState.account.id,
                                    currency = selectedCurrency
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}
