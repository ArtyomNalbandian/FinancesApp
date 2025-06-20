package com.example.financesapp.presentation.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financesapp.R
import com.example.financesapp.data.remote.RetrofitInstance
import com.example.financesapp.data.remote.repository.RemoteDataSourceImpl
import com.example.financesapp.domain.usecase.impl.GetAccountsUseCaseImpl
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.utils.toCurrencySymbol

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    onCreateAccount: () -> Unit,
    onEditAccount: () -> Unit,

    ) {
    val repository = remember { RemoteDataSourceImpl(RetrofitInstance.api) }
    val usecase = remember { GetAccountsUseCaseImpl(repository) }
    val viewModel: AccountViewModel = viewModel(
        factory = AccountViewModelFactory(usecase)
    )

    val currencySelectorState by viewModel.currencySelectorState.collectAsState()

    val state by viewModel.accountsState.collectAsState()
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(Unit) {
        viewModel.accountsEvent.collect { event ->
            when (event) {
                is AccountEvent.NavigateToCreateAccountScreen -> onCreateAccount()
                is AccountEvent.NavigateToEditAccountScreen -> onEditAccount()
                is AccountEvent.ShowError -> {}
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val currentState = state) {
            is AccountState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is AccountState.Error -> {
                Text(
                    text = currentState.message,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }

            is AccountState.Success -> {
                Column {
                    val account = currentState.account
                    ListItem(
                        title = "Баланс",
                        amount = currentState.account.balance + " ${account.currency.toCurrencySymbol()}",
                        backgroundColor = MaterialTheme.colorScheme.secondary,
                        leadingIcon = R.drawable.money,
                        trailingIcon = R.drawable.more_vert,
                        onClick = { viewModel.handleIntent(AccountIntent.EditAccount(account.id)) },
                        modifier = Modifier.height(56.dp)
                    )
                    HorizontalDivider()
                    ListItem(
                        title = "Валюта",
                        amount = account.currency.toCurrencySymbol(),
                        backgroundColor = MaterialTheme.colorScheme.secondary,
                        trailingIcon = R.drawable.more_vert,
                        onClick = {
                            viewModel.handleIntent(
                                AccountIntent.OpenCurrencySelector(
                                    account.id
                                )
                            )
                        },
                        modifier = Modifier.height(56.dp)
                    )
                }
            }

        }
        if (currencySelectorState is CurrencySelectorState.Visible) {
            val accountId =
                (currencySelectorState as CurrencySelectorState.Visible).selectedAccountId
            AccountBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    viewModel.handleIntent(AccountIntent.CloseCurrencySelector)
                },
                onCurrencySelected = { selectedCurrency ->
                    viewModel.handleIntent(
                        AccountIntent.ChangeCurrency(
                            accountId,
                            selectedCurrency
                        )
                    )
                }
            )
        }

    }
}