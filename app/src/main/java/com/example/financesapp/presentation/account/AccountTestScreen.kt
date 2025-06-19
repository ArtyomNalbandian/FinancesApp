//package com.example.financesapp.presentation.account
//
//import android.util.Log
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.ModalBottomSheet
//import androidx.compose.material3.Text
//import androidx.compose.material3.rememberModalBottomSheetState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.financesapp.R
//import com.example.financesapp.presentation.common.AddButton
//import com.example.financesapp.presentation.common.ListItem
//import com.example.financesapp.presentation.common.TopAppBarState
//import com.example.financesapp.presentation.common.TopAppBarStateProvider
//import com.example.financesapp.ui.theme.LightRed
//
//// Добавлен компонент AccountItem
//@Composable
//private fun AccountItem(
//    account: Account,
//    currencySymbol: String,
//    onCurrencyClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Column(modifier = modifier) {
//        ListItem(
//            title = account.name.ifEmpty { "Счет ${account.id}" },
//            amount = "${account.balance} $currencySymbol",
//            backgroundColor = MaterialTheme.colorScheme.surface,
//            trailingIcon = R.drawable.more_vert,
//            onClick = onCurrencyClick,
//            modifier = Modifier.height(56.dp)
//        )
//        HorizontalDivider()
//    }
//}
//
//@Composable
//fun AccountScreenSuperTest(
//    onEditClick: () -> Unit,
//    onAddAccount: () -> Unit,
//    viewModel: AccountsViewModel = viewModel()
//) {
//    val state by viewModel.state.collectAsState()
//    var showCurrencySheet by remember { mutableStateOf(false) }
//    var selectedAccountId by remember { mutableStateOf<Long?>(null) }
//
//    // Обработка событий
//    LaunchedEffect(Unit) {
//        viewModel.events.collect { event ->
//            when (event) {
//                is AccountEvent.ShowCurrencySelector -> {
//                    selectedAccountId = event.accountId
//                    showCurrencySheet = true
//                }
//
//                is AccountEvent.ShowError -> {
//                    // Показываем ошибку
//                }
//            }
//        }
//        viewModel.handleIntent(AccountIntent.LoadAccounts)
//    }
//
//    TopAppBarStateProvider.update(
//        TopAppBarState(
//            title = "Мой счет",
//            trailingIcon = R.drawable.ic_edit,
//            onTrailingIconClick = onEditClick
//        )
//    )
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        when (state) {
//            AccountState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
//            is AccountState.Error -> Text(
//                text = (state as AccountState.Error).message,
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier.align(Alignment.Center)
//            )
//
//            is AccountState.Content -> {
//                val contentState = state as AccountState.Content
//
//                val accounts = contentState.accounts
//                val mainAccount = accounts.firstOrNull()
//                LazyColumn(
//                    modifier = Modifier.fillMaxSize(),
//                    contentPadding = PaddingValues(bottom = 80.dp)
//                ) {
//                    item {
//                        BalanceAndCurrencySection(
//                            totalBalance = mainAccount?.balance ?: "0.00",
//                            currency = mainAccount?.currency ?: "blablabla",
//                            onCurrencyClick = { /* Можно добавить обработчик если нужно */ }
//                        )
//                    }
//
//                    item {
//                        Text(
//                            text = "Счета",
//                            style = MaterialTheme.typography.titleMedium,
//                            modifier = Modifier.padding(16.dp)
//                        )
//                    }
//                    // Добавлено отображение списка счетов
//                    items(contentState.accounts) { account ->
//                        val currencySymbol = getCurrencySymbol(
//                            contentState.selectedCurrency[account.id] ?: account.currency
//                        )
//                        AccountItem(
//                            account = account,
//                            currencySymbol = currencySymbol,
//                            onCurrencyClick = {
//                                viewModel.handleIntent(
//                                    AccountIntent.OpenCurrencySelector(account.id)
//                                )
//                            }
//                        )
//                    }
//
//
////                    items(contentState.accounts) { account ->
////                        val displayCurrency = contentState.selectedCurrency[account.id] ?: account.currency
////                        AccountListItem(
////                            account = account,
////                            displayCurrency = displayCurrency,
////                            onCurrencyClick = {
////                                viewModel.handleIntent(AccountIntent.OpenCurrencySelector(account.id))
////                            }
////                        )
////                    }
//                }
//            }
//        }
//
//        AddButton(
//            onClick = onAddAccount,
//            modifier = Modifier.align(Alignment.BottomEnd)
//        )
//    }
//
//    // BottomSheet для выбора валюты
//    if (showCurrencySheet && selectedAccountId != null) {
//        CurrencySelectionBottomSheet(
//            onDismiss = { showCurrencySheet = false },
//            onCurrencySelected = { currency ->
//                viewModel.handleIntent(
//                    AccountIntent.ChangeAccountCurrency(selectedAccountId!!, currency.code)
//                )
//                showCurrencySheet = false
//            }
//        )
//    }
//}
//
//@Composable
//private fun AccountListItem(
//    account: Account,
//    displayCurrency: String,
//    onCurrencyClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    ListItem(
//        title = account.name.ifEmpty { "Счет ${account.id}" },
//        amount = "${account.balance} ${getCurrencySymbol(displayCurrency)}",
//        backgroundColor = MaterialTheme.colorScheme.surface,
//        trailingIcon = R.drawable.more_vert,
//        onClick = onCurrencyClick,
//        modifier = modifier.height(56.dp)
//    )
//    HorizontalDivider()
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun CurrencySelectionBottomSheet(
//    onDismiss: () -> Unit,
//    onCurrencySelected: (CurrencyItem) -> Unit,
//) {
//    val currencies = listOf(
//        CurrencyItem("RUB", "Российский рубль", "₽", R.drawable.ic_ruble),
//        CurrencyItem("USD", "Американский доллар", "$", R.drawable.ic_dollar),
//        CurrencyItem("EUR", "Евро", "€", R.drawable.ic_euro)
//    )
//
//    ModalBottomSheet(onDismissRequest = onDismiss) {
//        Column {
//            currencies.forEach { currency ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable { onCurrencySelected(currency) }
//                        .padding(horizontal = 16.dp)
//                        .height(72.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        painter = painterResource(id = currency.iconRes),
//                        contentDescription = null,
//                        modifier = Modifier.size(28.dp)
//                    )
//                    Spacer(Modifier.width(16.dp))
//                    Text(text = currency.name, color = Color.Black)
//                }
//                HorizontalDivider()
//            }
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(color = LightRed)
//                    .clickable(onClick = onDismiss)
//                    .padding(horizontal = 16.dp)
//                    .height(72.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_cancel),
//                    contentDescription = "Отмена",
//                    modifier = Modifier.size(28.dp),
//                    tint = Color.White
//                )
//                Spacer(Modifier.width(16.dp))
//                Text(text = "Отмена", color = Color.White)
//            }
//        }
//    }
//}
//
//private fun getCurrencySymbol(currency: String): String {
//    return when (currency) {
//        "USD" -> "$"
//        "EUR" -> "€"
//        else -> "₽"
//    }
//}
//
//@Composable
//private fun BalanceAndCurrencySection(
//    totalBalance: String,
//    currency: String,
//    onCurrencyClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Column(modifier = modifier) {
//        ListItem(
//            title = "Баланс",
//            amount = totalBalance,
//            backgroundColor = MaterialTheme.colorScheme.secondary,
//            leadingIcon = R.drawable.money,
//            trailingIcon = R.drawable.more_vert,
//            onClick = {},
//            modifier = Modifier.height(56.dp)
//        )
//        HorizontalDivider()
//        ListItem(
//            title = "Валюта",
//            amount = currency, // Или другая валюта по умолчанию
//            backgroundColor = MaterialTheme.colorScheme.secondary,
//            trailingIcon = R.drawable.more_vert,
//            onClick = onCurrencyClick,
//            modifier = Modifier.height(56.dp)
//        )
//    }
//}
//
////@Composable
////fun AccountTestScreen(
////    onTrailingIconClick: () -> Unit,
////    onAddAccount: () -> Unit,
////    viewModel: AccountsViewModel = viewModel(factory = AccountsViewModel.Factory())
////) {
////    val state by viewModel.accountState.collectAsState()
////    var showCurrencySheet by remember { mutableStateOf(false) }
////
////    val onCurrencySelected: (CurrencyItem) -> Unit = { currency ->
////        showCurrencySheet = false
////    }
////
////    TopAppBarStateProvider.update(
////        TopAppBarState(
////            title = "Мой счет",
////            trailingIcon = R.drawable.ic_edit,
////            onTrailingIconClick = onTrailingIconClick
////        )
////    )
////
////    Box(
////        modifier = Modifier.fillMaxSize()
////    ) {
////        when {
////            state.isLoading -> {
////                CircularProgressIndicator(
////                    modifier = Modifier.align(Alignment.Center)
////                )
////            }
////
////            state.error != null -> {
////                Text(
////                    text = state.error!!,
////                    color = MaterialTheme.colorScheme.error,
////                    modifier = Modifier.align(Alignment.Center)
////                )
////            }
////
////            else -> {
////                LazyColumn(
////                    modifier = Modifier.fillMaxSize(),
////                    contentPadding = PaddingValues(bottom = 80.dp)
////                ) {
////                    item {
////                        Column {
////                            ListItem(
////                                title = "Баланс",
////                                amount = "-670 000 ₽",
////                                backgroundColor = MaterialTheme.colorScheme.secondary,
////                                leadingIcon = R.drawable.money,
////                                trailingIcon = R.drawable.more_vert,
////                                onClick = {}, //TODO()
////                                modifier = Modifier.height(56.dp)
////                            )
////                            HorizontalDivider()
////                            ListItem(
////                                title = "Валюта",
////                                amount = "₽",
////                                backgroundColor = MaterialTheme.colorScheme.secondary,
////                                trailingIcon = R.drawable.more_vert,
////                                onClick = { }, //TODO()
////                                modifier = Modifier.height(56.dp)
////                            )
////                            HorizontalDivider()
////                        }
////                    }
////
////                    item {
////                        Text(
////                            text = "Счета",
////                            style = MaterialTheme.typography.titleMedium,
////                            modifier = Modifier.padding(16.dp)
////                        )
////                    }
////
////                    items(state.accounts) { account ->
////                        AccountListItem(account = account)
////                    }
////                    Log.d("testLog", "accountList --- ${state.accounts}")
////                }
////            }
////        }
////
////        AddButton(
////            onClick = onAddAccount,
////            modifier = Modifier.align(Alignment.BottomEnd)
////        )
////    }
////}
////
////@Composable
////private fun AccountListItem(
////    account: Account,
////    modifier: Modifier = Modifier
////) {
////    ListItem(
////        title = account.name.ifEmpty { "Счет ${account.id}" },
////        amount = "${account.balance} ${account.currency}",
////        backgroundColor = MaterialTheme.colorScheme.surface,
////        trailingIcon = R.drawable.more_vert,
////        onClick = { /* TODO: Handle account click */ },
////        modifier = modifier.height(56.dp)
////    )
////    HorizontalDivider()
////}
////
////@OptIn(ExperimentalMaterial3Api::class)
////@Composable
////fun CurrencySelectionBottomSheet(
////    onDismiss: () -> Unit,
////    onCurrencySelected: (CurrencyItem) -> Unit,
////) {
////
////    ModalBottomSheet(
////        onDismissRequest = onDismiss,
////        sheetState = rememberModalBottomSheetState()
////    ) {
////        Column {
////            currencies.forEach { currency ->
////                Row(
////                    modifier = Modifier
////                        .fillMaxWidth()
////                        .clickable {
////                            onCurrencySelected(currency)
//////                            showSheet = false
//////                            viewModel.handleIntent(
//////                                AccountIntent.CurrencyClick(
//////                                    if (currency.name == "Российский рубль") "₽"
//////                                    else if (currency.name == "Американский доллар") "$"
//////                                    else "€"
//////                                )
//////                            )
////                        }
////                        .padding(horizontal = 16.dp)
////                        .height(72.dp),
////                    verticalAlignment = Alignment.CenterVertically
////                ) {
////                    Icon(
////                        painter = painterResource(id = currency.iconRes),
////                        contentDescription = null,
////                        modifier = Modifier.size(28.dp)
////                    )
////                    Spacer(Modifier.width(16.dp))
////                    Text(
////                        text = currency.name,
////                        color = Color.Black
////                    )
////                }
//////                if (index < currencies.size) {
////                    HorizontalDivider()
//////                }
////            }
////
////            Row(
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .background(color = LightRed)
////                    .clickable(onClick = onDismiss)
////                    .padding(horizontal = 16.dp)
////                    .height(72.dp),
////                verticalAlignment = Alignment.CenterVertically
////            ) {
////                Icon(
////                    painter = painterResource(id = R.drawable.ic_cancel),
////                    contentDescription = "Отмена",
////                    modifier = Modifier.size(28.dp),
////                    tint = Color.White
////                )
////                Spacer(Modifier.width(16.dp))
////                Text(
////                    text = "Отмена",
////                    color = Color.White,
////                )
////            }
////        }
////    }
////}
////
////data class CurrencyItemDepr(
////    val name: String = "Российский рубль",
////    val iconRes: Int
////)
////
////val currencies = listOf(
////    CurrencyItemDepr("Российский рубль", R.drawable.ic_ruble),
////    CurrencyItemDepr("Американский доллар", R.drawable.ic_dollar),
////    CurrencyItemDepr("Евро", R.drawable.ic_euro),
////)
//
//
////@Composable
////fun AccountTestScreen(
////    onTrailingIconClick: () -> Unit,
////    onAddAccount: () -> Unit,
////    viewModel: AccountsViewModel = viewModel(factory = AccountsViewModel.Factory())
////) {
////    TopAppBarStateProvider.update(
////        TopAppBarState(
////            title = "Мой счет",
////            trailingIcon = R.drawable.ic_edit,
////            onTrailingIconClick = onTrailingIconClick
////        )
////    )
////
////    Box(
////        modifier = Modifier.fillMaxSize()
////    ) {
////        Column {
////            ListItem(
////                title = "Баланс",
////                amount = "-670 000 ₽",
////                backgroundColor = MaterialTheme.colorScheme.secondary,
////                leadingIcon = R.drawable.money,
////                trailingIcon = R.drawable.more_vert,
////                onClick = {}, //TODO()
////                modifier = Modifier.height(56.dp)
////            )
////            HorizontalDivider()
////            ListItem(
////                title = "Валюта",
////                amount = "₽",
////                backgroundColor = MaterialTheme.colorScheme.secondary,
////                trailingIcon = R.drawable.more_vert,
////                onClick = { }, //TODO()
////                modifier = Modifier.height(56.dp)
////            )
////        }
////        AddButton(
////            onClick = onAddAccount,
////            modifier = Modifier.align(Alignment.BottomEnd)
////        )
////    }
////}