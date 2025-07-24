package com.example.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal fun SyncFrequencyScreen() {
    val viewModel: SettingsViewModel = viewModel()
    val frequency by viewModel.syncFrequency.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Частота синхронизации", style = MaterialTheme.typography.titleMedium)
        Text("${frequency} ч.", style = MaterialTheme.typography.bodyLarge)
        Slider(
            value = frequency.toFloat(),
            onValueChange = { viewModel.setSyncFrequency(it.toInt()) },
            valueRange = 1f..24f,
            steps = 23,
            modifier = Modifier.fillMaxWidth()
        )
        Text("Выберите, как часто приложение будет синхронизироваться с сервером.", style = MaterialTheme.typography.bodyMedium)
    }
}
