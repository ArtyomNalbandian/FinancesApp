package com.example.edit_account.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common.util.toCurrencyFromSymbol
import com.example.common.util.toCurrencySymbol
import com.example.database.di.DaggerDatabaseComponent
import com.example.network.di.DaggerNetworkComponent
import com.example.edit_account.di.DaggerEditAccountComponent
import com.example.ui.FinancesTopBarConfig
import com.example.ui.R
import com.example.ui.HapticsUtil
import com.example.edit_account.R.string

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditAccountScreen(
    navigateBack: () -> Unit,
) {

    val networkComponent = DaggerNetworkComponent.create()
    val databaseComponent = DaggerDatabaseComponent.builder()
        .context(LocalContext.current.applicationContext)
        .build()
    val editAccountComponent = DaggerEditAccountComponent
        .factory()
        .create(
            networkApi = networkComponent,
            databaseApi = databaseComponent
        )
    val editAccountViewModel: EditAccountViewModel = viewModel(factory = editAccountComponent.viewModelFactory())
    Log.d("testLog", "$editAccountComponent")

    val context = LocalContext.current
    val state by editAccountViewModel.state.collectAsStateWithLifecycle()
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
                    HapticsUtil.performHaptic(context)
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    FinancesTopBarConfig(
        title = { Text(stringResource(string.edit_account_title)) },
        actions = {
            IconButton(
                onClick = {
                    Log.d("testLog", "EditAccountScreen onClick --- accountId = $account")
                    editAccountViewModel.submit(name, balance, currency)
                    navigateBack()
                },
                enabled = (name.isNotBlank() && name != account?.name) ||
                        (balance.isNotBlank() && balance != account?.balance) ||
                        (currency.isNotBlank() && currency != account?.currency)
            ) {
                Icon(painterResource(R.drawable.ic_apply), contentDescription = stringResource(string.confirm))
            }
        },
        navAction = {
            IconButton(onClick = navigateBack) {
                Icon(painterResource(R.drawable.ic_back), contentDescription = stringResource(string.back))
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
                Text("${stringResource(string.error)}: $error", color = Color.Red)
            }

            is EditAccountState.Content -> {
                Column {
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(stringResource(string.account_name)) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(MaterialTheme.colorScheme.secondary),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                        )
                    )
                    HorizontalDivider()
                    TextField(
                        value = balance,
                        onValueChange = { balance = it },
                        label = { Text(stringResource(string.balance)) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(MaterialTheme.colorScheme.secondary),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                        )
                    )
                    HorizontalDivider()
                    com.example.ui.ListItem(
                        title = stringResource(string.currency),
                        amount = currentState.account.currency.toCurrencySymbol(),
                        backgroundColor = MaterialTheme.colorScheme.secondary,
                        trailingIcon = R.drawable.more_vert,
                        modifier = Modifier.height(56.dp),
                        onClick = {
                            editAccountViewModel.handleIntent(
                                EditAccountIntent.ShowCurrencySelector(
                                    currentState.account.id
                                )
                            )
                        }
                    )
                }
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
                                    currency = selectedCurrency.toCurrencyFromSymbol()
                                )
                            )
                            currency = selectedCurrency
                        }
                    )
                }
            }
        }
    }
}
