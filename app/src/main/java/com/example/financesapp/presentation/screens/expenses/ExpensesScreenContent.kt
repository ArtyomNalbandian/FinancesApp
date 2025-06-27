package com.example.financesapp.presentation.screens.expenses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financesapp.R
import com.example.financesapp.domain.models.expenses.Expense
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.utils.toCurrencySymbol

@Composable
fun ExpensesScreenContent(
    expenses: List<Expense>,
    amount: String,
    currency: String,
    onExpenseClick: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ListItem(
            title = "Всего",
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
                    onClick = { onExpenseClick(expense.id) }
                )
                HorizontalDivider()
            }
        }
    }
}
