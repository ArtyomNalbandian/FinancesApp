package com.example.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.FinancesTopBarConfig

@Composable
internal fun HapticsSettingsScreen(
    navigateBack: () -> Unit
) {
    val viewModel: SettingsViewModel = viewModel()
    val hapticsEnabled by viewModel.hapticsEnabled.collectAsStateWithLifecycle()
    val hapticsEffect by viewModel.hapticsEffect.collectAsStateWithLifecycle()
    val effects = viewModel.hapticsEffects

    FinancesTopBarConfig(
        title = { Text(stringResource(R.string.vibration)) },
        navAction = {
            IconButton(onClick = navigateBack) {
                Icon(painterResource(com.example.ui.R.drawable.ic_back), contentDescription = stringResource(R.string.back))
            }
        }
    )

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Text(stringResource(R.string.vibration), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = hapticsEnabled,
                onCheckedChange = { viewModel.setHapticsEnabled(it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.secondary,
                    checkedTrackColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
        if (hapticsEnabled) {
            Text(stringResource(R.string.vibration_effect), style = MaterialTheme.typography.bodyLarge)
            effects.forEachIndexed { idx, label ->
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    RadioButton(
                        selected = hapticsEffect == idx,
                        onClick = { viewModel.setHapticsEffect(idx) }
                    )
                    Text(stringResource(label))
                }
            }
        }
    }
}
