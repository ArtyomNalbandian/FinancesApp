package com.example.financesapp.presentation.screens.edit_account

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financesapp.R
import com.example.financesapp.domain.models.account.Account
import com.example.financesapp.presentation.common.FinancesTopBarConfig
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.presentation.screens.account.AccountIntent
import com.example.financesapp.presentation.screens.account.CurrencySelectorBottomSheet
import com.example.financesapp.utils.toCurrencySymbol

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAccountScreen(
    account: Account,
    viewModelFactory: ViewModelProvider.Factory,
    editAccountViewModel: EditAccountViewModel = viewModel(factory = viewModelFactory),
    navigateBack: () -> Unit,
) {

    val state by editAccountViewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState()

    FinancesTopBarConfig(
        title = { Text("Мой счет") },
        actions = {
            IconButton(
                onClick = {
                    Log.d("testLog", "EditAccountScreen onClick --- accountId = $account")
                    editAccountViewModel.handleIntent(EditAccountIntent.Submit((state as EditAccountState.Content).account))
                    navigateBack()
                }
            ) {
                Icon(painterResource(R.drawable.ic_apply), contentDescription = "Подтвердить")
            }
        },
        navAction = {
            IconButton(onClick = navigateBack) {
                Icon(painterResource(R.drawable.ic_back), contentDescription = "Назад")
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        when (val currentState = state) {
            is EditAccountState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is EditAccountState.Error -> {
                val error = (state as EditAccountState.Error).message
                Text("Ошибка: $error", color = Color.Red)
            }

            is EditAccountState.Content -> {
                val content = state as EditAccountState.Content
                EditAccountScreenContent(
                    account = account,
                    onCurrencySelectorClick = {
                        editAccountViewModel.handleIntent(
                            EditAccountIntent.ShowCurrencySelector(
                                currentState.account.id
                            )
                        )
                    }
                )
                if (currentState.isCurrencySelectorVisible) {
                    CurrencySelectorBottomSheet(
                        sheetState = sheetState,
                        onDismissRequest = {
                            editAccountViewModel.handleIntent(EditAccountIntent.HideCurrencySelector)
                        },
                        onCurrencySelected = { selectedCurrency ->
                            editAccountViewModel.handleIntent(
                                EditAccountIntent.ChangeCurrency(
                                    accountId = currentState.account.id,
                                    currency = selectedCurrency
                                )
                            )
                        }
                    )
                }

//                ExpensesScreenContent(
//                    expenses = content.expenses,
//                    amount = content.total,
//                    currency = content.currency,
//                    onExpenseClick = {})
//                AddButton(
//                    onClick = {}, modifier = Modifier.align(Alignment.BottomEnd)
//                )
            }
        }
    }
}

@Composable
fun EditAccountScreenContent(
    account: Account,
    onCurrencySelectorClick: () -> Unit
) {
    Column {
        ListItem(
            title = "Баланс",
            amount = account.balance + " ${account.currency.toCurrencySymbol()}",
            backgroundColor = MaterialTheme.colorScheme.secondary,
            leadingIcon = "\uD83D\uDCB0",
            trailingIcon = R.drawable.more_vert,
            onClick = { },
            modifier = Modifier.height(56.dp)
        )
        HorizontalDivider()
        ListItem(
            title = "Валюта",
            amount = account.currency.toCurrencySymbol(),
            backgroundColor = MaterialTheme.colorScheme.secondary,
            trailingIcon = R.drawable.more_vert,
            onClick = { onCurrencySelectorClick() },
            modifier = Modifier.height(56.dp)
        )
    }
}
