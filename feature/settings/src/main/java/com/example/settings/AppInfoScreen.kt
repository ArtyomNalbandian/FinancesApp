package com.example.settings

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.FinancesTopBarConfig
import com.example.ui.ListItem

@Composable
internal fun AppInfoScreen(
    navigateBack: () -> Unit
) {
    FinancesTopBarConfig(
        title = { Text(stringResource(R.string.app_info)) },
        navAction = {
            IconButton(onClick = navigateBack) {
                Icon(painterResource(com.example.ui.R.drawable.ic_back), contentDescription = stringResource(R.string.back))
            }
        }
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "💰",
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = stringResource(R.string.app_description),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            ListItem(
                title = stringResource(R.string.app_version),
                amount = stringResource(R.string.app_version_value)
            )
            HorizontalDivider()
        }

        item {
            ListItem(
                title = stringResource(R.string.app_developer),
                amount = stringResource(R.string.app_developer_value)
            )
            HorizontalDivider()
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "© 2025 FinancesApp Team",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
