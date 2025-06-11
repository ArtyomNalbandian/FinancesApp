package com.example.financesapp.presentation.expenses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.financesapp.data.mock.expenses
import com.example.financesapp.data.mock.expensesTotal
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.ui.theme.LightGreen

@Composable
fun ExpensesScreen() {
    Column {
        ListItem(
            title = expensesTotal.title,
            amount = expensesTotal.amount,
            backgroundColor = LightGreen,
            contentPadding = PaddingValues(vertical = 16.dp)
        )
        LazyColumn {
            items(expenses) { expense ->
                ListItem(
                    title = expense.title,
                    supportingText = expense.supportingText,
                    leadingIcon = expense.leadingIcon,
                    trailingIcon = expense.trailingIcon,
                    amount = expense.amount,
                    contentPadding = if (expense.supportingText != null) PaddingValues(vertical = 16.dp) else PaddingValues(vertical = 24.dp),
                    onClick = { } //TODO
                )
            }
        }
    }
}