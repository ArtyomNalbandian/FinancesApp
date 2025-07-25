package com.example.settings

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.FinancesTopBarConfig

@Composable
fun PinCodeScreen(
    mode: PinCodeMode = PinCodeMode.Check,
    onSuccess: () -> Unit = {},
    onSetMode: ((PinCodeMode) -> Unit)? = null,
    navigateBack: () -> Unit = {}
) {
    val viewModel: PinCodeViewModel = viewModel()
    val pin by viewModel.pin.collectAsStateWithLifecycle()
    val confirmPin by viewModel.confirmPin.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()
    val currentMode by viewModel.mode.collectAsStateWithLifecycle()
    val changeStep = viewModel.getChangeStep()

    FinancesTopBarConfig(
        title = { Text(stringResource(R.string.pin_code)) },
        navAction = {
            IconButton(onClick = navigateBack) {
                Icon(painterResource(com.example.ui.R.drawable.ic_back), contentDescription = stringResource(R.string.back))
            }
        }
    )

    val title = when (currentMode) {
        is PinCodeMode.Set -> stringResource(R.string.pin_create)
        is PinCodeMode.Confirm -> stringResource(R.string.pin_repeat)
        is PinCodeMode.Check -> stringResource(R.string.pin_enter)
        is PinCodeMode.Change -> when (changeStep) {
            0 -> stringResource(R.string.pin_old)
            1 -> stringResource(R.string.pin_new)
            2 -> stringResource(R.string.pin_repeat_new)
            else -> stringResource(R.string.pin_change)
        }
    }

    val code = when (currentMode) {
        is PinCodeMode.Confirm -> confirmPin
        is PinCodeMode.Change -> if (changeStep == 2) confirmPin else pin
        else -> pin
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(24.dp))
        PinCodeDots(
            code = code,
            error = error != null
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (error != null) {
            Text(stringResource(
                when (error) {
                    "Пин-коды не совпадают" -> R.string.pin_error_mismatch
                    "Неверный пин-код" -> R.string.pin_error_wrong
                    "Неверный старый пин-код" -> R.string.pin_error_wrong_old
                    else -> R.string.error
                }
            ), color = Color.Red, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }
        PinPad(
            onDigit = { viewModel.onDigit(it) { viewModel.onSubmit(onSuccess) } },
            onDelete = { viewModel.onDelete() },
            onSubmit = { viewModel.onSubmit(onSuccess) },
            codeLength = code.length
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (currentMode == PinCodeMode.Check && onSetMode != null) {
            Button(onClick = { viewModel.setMode(PinCodeMode.Set); onSetMode(PinCodeMode.Set) }) {
                Text(stringResource(R.string.pin_set_new))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { viewModel.setMode(PinCodeMode.Change); onSetMode(PinCodeMode.Change) }) {
                Text(stringResource(R.string.pin_change))
            }
        }
        if (currentMode == PinCodeMode.Set || currentMode == PinCodeMode.Confirm || currentMode == PinCodeMode.Change) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { viewModel.onSubmit(onSuccess) }, enabled = code.length == 4) {
                Text(stringResource(R.string.ok))
            }
        }
    }
}

@Composable
private fun PinCodeDots(code: String, error: Boolean) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        repeat(4) { idx ->
            val filled = idx < code.length
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(
                        color = when {
                            error -> Color.Red
                            filled -> MaterialTheme.colorScheme.primary
                            else -> Color.LightGray
                        },
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
private fun PinPad(
    onDigit: (Char) -> Unit,
    onDelete: () -> Unit,
    onSubmit: () -> Unit,
    codeLength: Int
) {
    val digits = listOf(
        listOf('1', '2', '3'),
        listOf('4', '5', '6'),
        listOf('7', '8', '9'),
        listOf(null, '0', '←')
    )
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        digits.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { digit ->
                    when (digit) {
                        null -> Spacer(modifier = Modifier.size(56.dp))
                        '←' -> PinPadButton(label = "←", onClick = onDelete)
                        else -> PinPadButton(label = digit.toString(), onClick = {
                            if (codeLength < 4) onDigit(digit)
                            if (codeLength == 3) onSubmit()
                        })
                    }
                }
            }
        }
    }
}

@Composable
private fun PinPadButton(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(MaterialTheme.colorScheme.secondary, shape = CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(label, style = MaterialTheme.typography.titleLarge)
    }
}
