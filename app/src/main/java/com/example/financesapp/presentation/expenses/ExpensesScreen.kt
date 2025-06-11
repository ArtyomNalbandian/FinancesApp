package com.example.financesapp.presentation.expenses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financesapp.data.mock.expenses
import com.example.financesapp.data.mock.expensesTotal
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.presentation.common.TestListItem
import com.example.financesapp.ui.theme.CardItemBackground
import com.example.financesapp.ui.theme.LightGreen

@Composable
fun ExpensesScreen() {
    Column {
//        ListItem(
//            name = expensesTotal.title,
//            amount = expensesTotal.amount,
//            backgroundColor = LightGreen,
////            modifier = Modifier.background(LightGreen)
//        )
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(LightGreen)
//                .padding(16.dp)
//        ) {
//            Text(
//                text = expensesTotal.title,
//            )
//            expensesTotal.amount?.let {
//                Text(
//                    text = it
//                )
//            }
//        }
        TestListItem(
            title = expensesTotal.title,
            amount = expensesTotal.amount,
            backgroundColor = LightGreen,
            contentPadding = PaddingValues(vertical = 16.dp)
        )

        LazyColumn {
            items(expenses) { expense ->
                TestListItem(
                    title = expense.title,
                    supportingText = expense.supportingText,
                    leadingIcon = expense.leadingIcon,
                    trailingIcon = expense.trailingIcon,
                    amount = expense.amount,
                    contentPadding = if (expense.supportingText != null) PaddingValues(vertical = 16.dp) else PaddingValues(vertical = 24.dp),
                    onClick = {  } //TODO
                )
//                ListItem(
//                    name = expense.title,
//                    supportingText = expense.supportingText,
//                    leadingIcon = expense.leadingIcon,
//                    trailingIcon = expense.trailingIcon,
//                    amount = expense.amount,
//                    backgroundColor = CardItemBackground
//                )
            }
        }
    }
}