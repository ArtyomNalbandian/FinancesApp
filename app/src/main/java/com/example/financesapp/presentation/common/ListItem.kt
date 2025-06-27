package com.example.financesapp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.example.financesapp.ui.theme.SupportingTextColor

@Composable
fun ListItem(
    title: String,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    amount: String? = null,
    leadingIcon: String? = null,
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
            .conditionalClickable(onClick)
            .padding(horizontal = 16.dp)
            .height(72.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LeadingContent(
            title = title,
            supportingText = supportingText,
            leadingIcon = leadingIcon
        )
        TrailingContent(
            amount = amount,
            currency = currency,
            trailingIcon = trailingIcon,
            trailingComposable = trailingComposable
        )
    }
}

@Composable
private fun LeadingContent(
    title: String,
    supportingText: String?,
    leadingIcon: String?
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        leadingIcon?.let { icon ->
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFD4FAE6),
                        shape = CircleShape
                    )
                    .size(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    fontSize = 16.sp,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            supportingText?.takeIf { it.isNotEmpty() }?.let { text ->
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall,
                    color = SupportingTextColor
                )
            }
        }
    }
}

@Composable
private fun TrailingContent(
    amount: String?,
    currency: String?,
    trailingIcon: Int?,
    trailingComposable: @Composable (() -> Unit)?
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        amount?.let {
            Text(
                text = if (currency != null) "$it $currency" else it,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        trailingIcon?.let { icon ->
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painter = painterResource(icon),
                contentDescription = "Trailing Icon",
            )
        }
        trailingComposable?.invoke()
    }
}

private fun Modifier.conditionalClickable(onClick: (() -> Unit)?): Modifier {
    return onClick?.let { clickable { it() } } ?: this
}
