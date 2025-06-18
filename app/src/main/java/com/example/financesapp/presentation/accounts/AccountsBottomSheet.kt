package com.example.financesapp.presentation.accounts

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
import androidx.compose.ui.unit.dp
import com.example.financesapp.R
import com.example.financesapp.domain.account.CurrencyItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onCurrencySelected: (String) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.tertiary
    ) {
        Column {
            currencies.forEachIndexed { index, currency ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onCurrencySelected(currency.symbol)
                        }
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
                HorizontalDivider()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red)
                    .clickable { onDismissRequest() }
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
                    text = "Отмена",
                    color = Color.White,
                )
            }
        }
    }
}

val currencies = listOf(
    CurrencyItem("Российский рубль", R.drawable.ic_ruble, "₽"),
    CurrencyItem("Американский доллар", R.drawable.ic_dollar, "$"),
    CurrencyItem("Евро", R.drawable.ic_euro, "€")
)