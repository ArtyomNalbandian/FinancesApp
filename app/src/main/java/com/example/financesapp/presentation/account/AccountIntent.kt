package com.example.financesapp.presentation.account

//sealed interface AccountIntent {
//    data object LoadAccounts : AccountIntent
//    data object OpenCurrencySelector : AccountIntent
//    data object CloseCurrencySelector : AccountIntent
//    data class SelectCurrency(val currency: CurrencyItem) : AccountIntent
//    data class BalanceClick(val accountId: String) : AccountIntent
//}

//sealed interface AccountIntent {
//    object LoadAccounts : AccountIntent
//    data class ChangeAccountCurrency(val accountId: Long, val currency: String) : AccountIntent
//    data class OpenCurrencySelector(val accountId: Long) : AccountIntent
//}

sealed interface AccountIntent {
//    data object LoadAccounts : AccountIntent
    data class OpenCurrencySelector(val accountId: Int) : AccountIntent
    data object CloseCurrencySelector : AccountIntent
    data class ChangeCurrency(val accountId: Int, val currency: String) : AccountIntent
    data class EditAccount(val accountId: Int) : AccountIntent
    data object CreateAccount : AccountIntent
}