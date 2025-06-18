package com.example.financesapp.presentation.accounts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financesapp.R
import com.example.financesapp.domain.account.Account
import com.example.financesapp.presentation.common.ListItem

@Composable
fun AccountsListItem(
    account: Account,
    editAccount: () -> Unit,
    changeCurrency: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        ListItem(
            title = account.name,
            backgroundColor = MaterialTheme.colorScheme.secondary,
            modifier = modifier.height(56.dp)
        )
        HorizontalDivider()
        ListItem(
            title = "Баланс",
            amount = account.balance + " ${account.currency}",
            backgroundColor = MaterialTheme.colorScheme.tertiary,
            leadingIcon = R.drawable.money,
            trailingIcon = R.drawable.more_vert,
            onClick = editAccount,
            modifier = Modifier.height(56.dp)
        )
        HorizontalDivider()
        ListItem(
            title = "Валюта",
            amount = account.currency,
            backgroundColor = MaterialTheme.colorScheme.tertiary,
            trailingIcon = R.drawable.more_vert,
            onClick = changeCurrency,
            modifier = Modifier.height(56.dp)
        )
        HorizontalDivider()
    }
}
