package com.example.edit_account.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.common.model.account.CurrencyItem
import com.example.edit_account.R
import com.example.edit_account.R.string

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CurrencySelectorBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onCurrencySelected: (String) -> Unit,
) {
    val currencies = getCurrencies()
    
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.tertiary
    ) {
        Column {
            currencies.forEachIndexed { _, currency ->
                Currencies(
                    currency = currency,
                    onSelected = { onCurrencySelected(currency.symbol) }
                )
                HorizontalDivider()
            }
            CancelButton(onClick = onDismissRequest)
        }
    }
}

@Composable
private fun getCurrencies(): List<CurrencyItem> {
    return listOf(
        CurrencyItem(stringResource(string.russian_ruble), R.drawable.ic_ruble, "₽"),
        CurrencyItem(stringResource(string.us_dollar), R.drawable.ic_dollar, "$"),
        CurrencyItem(stringResource(string.euro), R.drawable.ic_euro, "€")
    )
}

@Composable
private fun Currencies(
    currency: CurrencyItem,
    onSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelected)
            .padding(horizontal = 16.dp)
            .height(72.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = currency.iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = currency.name,
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun CancelButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp)
            .height(72.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_cancel_rounded),
            contentDescription = null,
            modifier = Modifier.size(28.dp),
            tint = Color.White
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = stringResource(string.cancel),
            color = Color.White,
        )
    }
}
