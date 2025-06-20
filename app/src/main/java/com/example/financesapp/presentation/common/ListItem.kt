package com.example.financesapp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financesapp.ui.theme.CardItemBackground
import com.example.financesapp.ui.theme.SupportingTextColor

@Composable
fun ListItem(
    title: String,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    amount: String? = null,
    leadingIcon: Int? = null,
    leadingIconStr: String? = null,
    trailingIcon: Int? = null,
    currency: String? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.tertiary,
    trailingComposable: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .then(onClick?.let { Modifier.clickable { it() } } ?: Modifier)
            .padding(horizontal = 16.dp)
            .height(72.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIconStr != null) {
                Text(
                    text = leadingIconStr,
                    fontSize = 22.sp
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            if (leadingIcon != null) {
                Image(
                    painter = painterResource(leadingIcon),
                    contentDescription = "Leading Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )
                if (supportingText != "" && supportingText != null) {
                    Text(
                        text = supportingText,
                        style = MaterialTheme.typography.bodySmall,
                        color = SupportingTextColor
                    )
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (amount != null) {
                Text(
                    text = if (currency != null) "$amount $currency" else amount,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            if (trailingIcon != null) {
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    painter = painterResource(trailingIcon),
                    contentDescription = "Trailing Icon",
                )
            }
            if (trailingComposable != null) {
                trailingComposable()
            }
        }
    }
}