package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListItem(
    title: String,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    supportingTrailingText: String? = null,
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
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .heightIn(min = 64.dp),
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
            supportingTrailingText = supportingTrailingText,
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
        leadingIcon?.let { emoji ->
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = Color(0xFFD4FAE6),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = emoji,
                    fontSize = 18.sp,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Default
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            supportingText?.takeIf { it.isNotBlank() }?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Composable
private fun TrailingContent(
    amount: String?,
    supportingTrailingText: String?,
    currency: String?,
    trailingIcon: Int?,
    trailingComposable: @Composable (() -> Unit)?
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column {
            amount?.let {
                Text(
                    text = if (!currency.isNullOrBlank()) "$it $currency" else it,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            supportingTrailingText?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        trailingIcon?.let { iconResId ->
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                tint = LocalContentColor.current
            )
        }

        trailingComposable?.invoke()
    }
}

private fun Modifier.conditionalClickable(onClick: (() -> Unit)?): Modifier {
    return if (onClick != null) clickable(onClick = onClick) else this
}