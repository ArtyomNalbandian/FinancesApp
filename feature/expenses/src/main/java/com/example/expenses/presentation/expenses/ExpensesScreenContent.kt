package com.example.expenses.presentation.expenses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.common.model.expense.Expense
import com.example.common.util.toCurrencySymbol
import com.example.ui.ListItem
import com.example.ui.R
import com.example.expenses.R.string

@Composable
internal fun ExpensesScreenContent(
    expenses: List<Expense>,
    amount: String,
    currency: String,
    onExpenseClick: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ListItem(
            title = stringResource(string.total),
            amount = "$amount ${currency.toCurrencySymbol()}",
            backgroundColor = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.height(56.dp)
        )
        HorizontalDivider()
        LazyColumn {
            items(expenses) { expense ->
                ListItem(
                    title = expense.title,
                    supportingText = expense.subtitle,
                    leadingIcon = expense.leadingIcon,
                    trailingIcon = R.drawable.more_vert,
                    amount = expense.amount,
                    currency = currency,
                    onClick = { onExpenseClick(expense.id) }
                )
                HorizontalDivider()
            }
        }
    }
}
