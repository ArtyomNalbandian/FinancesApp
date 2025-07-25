package com.example.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.res.stringResource
import com.example.ui.FinancesTopBarConfig

@Composable
internal fun ColorPickerScreen() {
    val viewModel: SettingsViewModel = viewModel()
    val selectedPaletteId by viewModel.paletteId.collectAsStateWithLifecycle()
    val palettes = viewModel.colorPalettes

    FinancesTopBarConfig(
        title = { Text(stringResource(R.string.main_color)) }
    )

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(stringResource(R.string.choose_color_scheme), style = MaterialTheme.typography.titleMedium)
        palettes.forEachIndexed { idx, palette ->
            Card(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clickable { viewModel.setPaletteId(idx) },
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(if (selectedPaletteId == idx) 8.dp else 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(if (selectedPaletteId == idx) Color.LightGray.copy(alpha = 0.2f) else Color.Transparent),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        BoxColor(palette.primary)
                        BoxColor(palette.secondary)
                        BoxColor(palette.tertiary)
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(stringResource(palette.nameRes), style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Composable
private fun BoxColor(colorInt: Int) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .size(32.dp)
            .background(Color(colorInt), shape = RoundedCornerShape(8.dp))
    )
}
