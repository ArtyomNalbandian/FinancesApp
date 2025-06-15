package com.example.financesapp.presentation.expenses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.example.financesapp.data.mock.expenses
import com.example.financesapp.data.mock.expensesTotal
import com.example.financesapp.presentation.common.ListItem

@Composable
fun ExpensesScreen() {
    Box(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.fillMaxSize()) {
            ListItem(
                title = expensesTotal.title,
                amount = expensesTotal.amount,
                backgroundColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.height(56.dp)
            )
            HorizontalDivider()
            LazyColumn {
                items(expenses) { expense ->
                    ListItem(
                        title = expense.title,
                        supportingText = expense.supportingText,
                        leadingIcon = expense.leadingIcon,
                        trailingIcon = expense.trailingIcon,
                        amount = expense.amount,
                        onClick = { } //TODO
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}