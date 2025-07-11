package com.example.account.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.common.model.account.Account
import com.example.common.util.toCurrencySymbol
import com.example.ui.ListItem
import com.example.ui.R

@Composable
fun AccountScreenContent(
    account: Account,
) {
    Column {
        ListItem(
            title = "Баланс",
            amount = account.balance + " ${account.currency.toCurrencySymbol()}",
            backgroundColor = MaterialTheme.colorScheme.secondary,
            leadingIcon = "\uD83D\uDCB0",
            trailingIcon = R.drawable.more_settings,
            modifier = Modifier.height(56.dp)
        )
        HorizontalDivider()
        ListItem(
            title = "Валюта",
            amount = account.currency.toCurrencySymbol(),
            backgroundColor = MaterialTheme.colorScheme.secondary,
            trailingIcon = R.drawable.more_settings,
            modifier = Modifier.height(56.dp)
        )
    }
}
