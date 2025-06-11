package com.example.financesapp.presentation.income

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
import com.example.financesapp.data.mock.Expenses
import com.example.financesapp.data.mock.expensesTotal
import com.example.financesapp.data.mock.income
import com.example.financesapp.data.mock.incomeTotal
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.presentation.common.TestListItem
import com.example.financesapp.ui.theme.LightGreen

@Composable
fun IncomeScreen() {
    Column {
//        ListItem(
//            name = incomeTotal.title,
//            amount = incomeTotal.amount,
//            modifier = Modifier.background(LightGreen)
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
//                text = incomeTotal.title,
//            )
//            incomeTotal.amount?.let {
//                Text(
//                    text = it
//                )
//            }
//        }
        TestListItem(
            title = incomeTotal.title,
            amount = incomeTotal.amount,
            backgroundColor = LightGreen,
            contentPadding = PaddingValues(vertical = 16.dp)
        )
//        HorizontalDivider()
        LazyColumn {
            items(income) { income ->
//                ListItem(
//                    name = income.title,
//                    trailingIcon = income.trailingIcon,
//                    amount = income.amount
//                )
                TestListItem(
                    title = income.title,
                    trailingIcon = income.trailingIcon,
                    amount = income.amount,
                    onClick = {  } //TODO
                )
            }
        }
    }
}