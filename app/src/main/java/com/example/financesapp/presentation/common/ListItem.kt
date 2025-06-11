package com.example.financesapp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.financesapp.ui.theme.CardItemBackground
import com.example.financesapp.ui.theme.PurpleGrey40
import com.example.financesapp.ui.theme.SupportingTextColor

@Composable
fun TestListItem(
    title: String,
    supportingText: String? = null,
    amount: String? = null,
    leadingIcon: Int? = null,
    trailingIcon: Int? = null,
    contentPadding: PaddingValues = PaddingValues(vertical = 24.dp),
    backgroundColor: Color = CardItemBackground,
    trailingComposable: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .then(onClick?.let { Modifier.clickable { it() } } ?: Modifier)
            .padding(contentPadding)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                if (supportingText != null) {
                    Text(
                        text = supportingText,
                        style = MaterialTheme.typography.bodyMedium,
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
                    text = amount,
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
    HorizontalDivider()
}

@Composable
fun ListItemTest(
    name: String,
    supportingText: String? = null,
    amount: String? = null,
    trailingIcon: Int? = null,
    leadingIcon: Int? = null,
    onClick: () -> Unit = {},
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
//            .background(CardItemBackground)
//            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (leadingIcon != null) {
                    Image(
                        painter = painterResource(leadingIcon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }

                Column {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    if (supportingText != null) {
                        Text(
                            text = supportingText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = SupportingTextColor
                        )
                    }
                }

            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (amount != null) {
                    Text(
                        text = amount,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(end = if (trailingIcon != null) 8.dp else 0.dp)
                    )
                }

                if (trailingIcon != null) {
                    Icon(
                        painter = painterResource(trailingIcon),
                        contentDescription = "Leading Icon",
                    )
                }
            }
        }
    }
    HorizontalDivider()
}

@Composable
fun ListItem(
    name: String,
    supportingText: String? = null,
    amount: String? = null,
    trailingIcon: Int? = null,
    leadingIcon: Int? = null,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier, // Дефолтное значение
    backgroundColor: Color = MaterialTheme.colorScheme.surface, // Новый параметр
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor) // Используем параметр
                .clickable(onClick = onClick)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    leadingIcon?.let {
                        Image(
                            painter = painterResource(it),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }

                    Column {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        supportingText?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    amount?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(end = if (trailingIcon != null) 8.dp else 0.dp)
                        )
                    }

                    trailingIcon?.let {
                        Icon(
                            painter = painterResource(it),
                            contentDescription = "Trailing Icon",
                        )
                    }
                }
            }
        }
        HorizontalDivider()
    }
}