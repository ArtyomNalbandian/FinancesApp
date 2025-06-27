package com.example.financesapp.presentation.screens.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financesapp.R
import com.example.financesapp.domain.models.account.Account
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.utils.toCurrencySymbol

@Composable
fun AccountScreenContent(
    account: Account,
    onCurrencySelectorClick: () -> Unit
) {
    Column {
        ListItem(
            title = "Баланс",
            amount = account.balance + " ${account.currency.toCurrencySymbol()}",
            backgroundColor = MaterialTheme.colorScheme.secondary,
            leadingIcon = R.drawable.money,
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