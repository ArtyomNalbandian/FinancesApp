//package com.example.financesapp.presentation.accounts
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Text
//import androidx.compose.material3.rememberModalBottomSheetState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.financesapp.R
//import com.example.financesapp.presentation.common.AddButton
//import com.example.financesapp.presentation.common.TopAppBarState
//import com.example.financesapp.presentation.common.TopAppBarStateProvider
//import kotlinx.coroutines.launch
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AccountsScreen(
//    viewModel: AccountsViewModel = viewModel(),
//    navigateToEditAccountScreen: () -> Unit,
//    navigateToCreateAccountScreen: () -> Unit,
//) {
//    TopAppBarStateProvider.update(
//        TopAppBarState(
//            title = "Мои счета",
//            trailingIcon = R.drawable.ic_edit,
//            onTrailingIconClick = {}
//        )
//    )
//
//    val accountsState by viewModel.accountsState.collectAsState()
//    val currencySelectorState by viewModel.currencySelectorState.collectAsState()
//
//    val sheetState = rememberModalBottomSheetState()
//    val coroutineScope = rememberCoroutineScope()
//
//    LaunchedEffect(currencySelectorState) {
//        if (currencySelectorState is CurrencySelectorState.Visible) {
//            coroutineScope.launch { sheetState.show() }
//        } else {
//            coroutineScope.launch { sheetState.hide() }
//        }
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        when (accountsState) {
//            is AccountsState.Loading -> Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator(modifier = Modifier.size(100.dp))
//            }
//
//            is AccountsState.Error -> Text(
//                text = "Ошибка: ${(accountsState as AccountsState.Error).message}",
//                modifier = Modifier.align(Alignment.Center)
//            )
//
//            is AccountsState.Success -> {
//                val accounts = (accountsState as AccountsState.Success).accounts
//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxSize()
//                ) {
//                    items(accounts) { account ->
//                        AccountsListItem(
//                            account = account,
//                            editAccount = {
//                                viewModel.handleIntent(AccountsIntent.EditAccount(account.id))
//                                navigateToEditAccountScreen()
//                            },
//                            changeCurrency = {
//                                viewModel.handleIntent(AccountsIntent.OpenCurrencySelector(account.id))
//                            }
//                        )
//                    }
//                }
//            }
//        }
//
//        AddButton(
//            onClick = { navigateToCreateAccountScreen() },
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//        )
//
//        if (currencySelectorState is CurrencySelectorState.Visible) {
//            val accountId =
//                (currencySelectorState as CurrencySelectorState.Visible).selectedAccountId
//            AccountsBottomSheet(
//                sheetState = sheetState,
//                onDismissRequest = {
//                    viewModel.handleIntent(AccountsIntent.CloseCurrencySelector)
//                },
//                onCurrencySelected = { selectedCurrency ->
//                    viewModel.handleIntent(
//                        AccountsIntent.ChangeCurrency(
//                            accountId,
//                            selectedCurrency
//                        )
//                    )
//                }
//            )
//        }
//    }
//}
