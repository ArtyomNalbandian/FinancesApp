package com.example.financesapp.presentation.screens.edit_account

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financesapp.R
import com.example.financesapp.domain.models.account.Account
import com.example.financesapp.presentation.common.FinancesTopBarConfig
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.presentation.screens.account.CurrencySelectorBottomSheet
import com.example.financesapp.utils.toCurrencySymbol

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAccountScreen(
    viewModelFactory: ViewModelProvider.Factory,
    editAccountViewModel: EditAccountViewModel = viewModel(factory = viewModelFactory),
    navigateBack: () -> Unit,
) {

    val context = LocalContext.current
    val state by editAccountViewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState()

    val account by editAccountViewModel.editAccountState.collectAsStateWithLifecycle()
    var name by remember(account) { mutableStateOf(account?.name.orEmpty()) }
    var balance by remember(account) { mutableStateOf(account?.balance.orEmpty()) }
    var currency by remember(account) { mutableStateOf(account?.currency.orEmpty()) }

    LaunchedEffect(Unit) {
        editAccountViewModel.event.collect { event ->
            when (event) {
                is EditAccountEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is EditAccountEvent.ShowSuccess -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    FinancesTopBarConfig(
        title = { Text("Редактирование счета") },
        actions = {
            IconButton(
                onClick = {
                    Log.d("testLog", "EditAccountScreen onClick --- accountId = $account")
                    editAccountViewModel.submit(name, balance, currency)
                    navigateBack()
                },
                enabled = (name.isNotBlank() && name != account?.name) ||
                        (balance.isNotBlank() && balance != account?.balance)
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
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Название счета") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    OutlinedTextField(
                        value = balance,
                        onValueChange = { balance = it },
                        label = { Text("Баланс") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
//                    Button(
//                        onClick = { editAccountViewModel.onNameChange("Тест") }
//                    ) {
//                        Text("Тест")
//                    }
//                    Button(
//                        onClick = { editAccountViewModel.onBalanceChange("1000") }
//                    ) {
//                        Text("Тест2")
//                    }
//                    ListItem(
//                        title = "Баланс",
//                        amount = account.balance + " ${account.currency.toCurrencySymbol()}",
//                        backgroundColor = MaterialTheme.colorScheme.secondary,
//                        leadingIcon = "\uD83D\uDCB0",
//                        trailingIcon = R.drawable.more_vert,
//                        onClick = { },
//                        modifier = Modifier.height(56.dp)
//                    )
//                    HorizontalDivider()
//                    ListItem(
//                        title = "Валюта",
//                        amount = account.currency.toCurrencySymbol(),
//                        backgroundColor = MaterialTheme.colorScheme.secondary,
//                        trailingIcon = R.drawable.more_vert,
//                        onClick = {
//                            editAccountViewModel.toggleCurrencySelector()
//                        },
//                        modifier = Modifier.height(56.dp)
//                    )
                }
//                EditAccountScreenContent(
//                    account = account,
//                    onCurrencySelectorClick = {
//                        editAccountViewModel.toggleCurrencySelector()
//                        editAccountViewModel.handleIntent(
//                            EditAccountIntent.ShowCurrencySelector(
//                                currentState.account.id
//                            )
//                        )
//                    }
//                )
                if (currentState.isCurrencySelectorVisible) {
                    CurrencySelectorBottomSheet(
                        sheetState = sheetState,
                        onDismissRequest = {
//                            editAccountViewModel.toggleCurrencySelector()
//                            editAccountViewModel.handleIntent(EditAccountIntent.HideCurrencySelector)
                        },
                        onCurrencySelected = { selectedCurrency ->
//                            editAccountViewModel.onCurrencyChange(selectedCurrency)
//                            editAccountViewModel.handleIntent(
//                                EditAccountIntent.ChangeCurrency(
//                                    accountId = currentState.account.id,
//                                    currency = selectedCurrency
//                                )
//                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EditAccountScreenContent(
    account: Account,
    onCurrencySelectorClick: () -> Unit,
    onBalanceChange: () -> Unit,
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary)
                .height(56.dp)
        ) {
            Text(text = "Баланс")
            Text(text = "Название счета")

        }
//        ListItem(
//            title = "Баланс",
//            amount = account.balance + " ${account.currency.toCurrencySymbol()}",
//            backgroundColor = MaterialTheme.colorScheme.secondary,
//            leadingIcon = "\uD83D\uDCB0",
//            trailingIcon = R.drawable.more_vert,
//            onClick = { },
//            modifier = Modifier.height(56.dp)
//        )
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
