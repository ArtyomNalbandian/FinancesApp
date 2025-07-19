package com.example.expenses.presentation.expenses_add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common.util.toCurrencySymbol
import com.example.database.di.DaggerDatabaseComponent
import com.example.expenses.di.DaggerExpensesComponent
import com.example.network.di.DaggerNetworkComponent
import com.example.ui.FinancesTopBarConfig
import com.example.ui.ListItem
import com.example.ui.R
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ExpensesAddScreen(
    navigateBack: () -> Unit
) {

    val networkComponent = DaggerNetworkComponent.create()
    val databaseComponent = DaggerDatabaseComponent.builder()
        .context(LocalContext.current.applicationContext)
        .build()
    val expensesComponent = DaggerExpensesComponent
        .factory()
        .create(
            networkApi = networkComponent,
            databaseApi = databaseComponent
        )
    val expensesAddViewModel: ExpensesAddViewModel =
        viewModel(factory = expensesComponent.viewModelFactory())

    val expensesAddState by expensesAddViewModel.state.collectAsStateWithLifecycle()

    FinancesTopBarConfig(
        title = { Text("Новый расход") },
        navAction = {
            IconButton(onClick = navigateBack) {
                Icon(painterResource(R.drawable.ic_cancel), contentDescription = "Отмена")
            }
        },
        actions = {
            IconButton(
                onClick = {
                    expensesAddViewModel.handleIntent(ExpensesAddIntent.CreateExpense)
                },
                enabled = expensesAddState.isFormValid && !expensesAddState.isCreating
            ) {
                if (expensesAddState.isCreated) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        painterResource(R.drawable.ic_apply),
                        contentDescription = "Создать"
                    )
                }

            }
        }
    )

    var showCategoryMenu by remember { mutableStateOf(false) }
    var showAmountDialog by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showCommentDialog by remember { mutableStateOf(false) }

    var amountText by remember { mutableStateOf("") }
    var commentText by remember { mutableStateOf("") }

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    LaunchedEffect(expensesAddState.isCreated) {
        if (expensesAddState.isCreated) {
            navigateBack()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.tertiary)
    ) {
        when {
            expensesAddState.isLoading -> {
                CircularProgressIndicator()
            }

            expensesAddState.error != null -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = expensesAddState.error!!)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        expensesAddViewModel.handleIntent(ExpensesAddIntent.ClearError)
                    }) {
                        Text("Повторить")
                    }
                }
            }

            else -> {
                Column {
                    ListItem(
                        title = "Счет",
                        amount = expensesAddState.account!!.name
                    )
                    HorizontalDivider()
                    ExposedDropdownMenuBox(
                        expanded = showCategoryMenu,
                        onExpandedChange = { showCategoryMenu = !showCategoryMenu }
                    ) {
                        ListItem(
                            modifier = Modifier.menuAnchor(),
                            title = "Статья",
                            amount = expensesAddState.selectedCategory?.let { "${it.emoji} ${it.name}" }
                                ?: "Выберите статью",
                            trailingIcon = R.drawable.more,
                            onClick = { showCategoryMenu = true }
                        )
                        ExposedDropdownMenu(
                            expanded = showCategoryMenu,
                            onDismissRequest = { showCategoryMenu = false }
                        ) {
                            expensesAddState.categories.forEach { category ->
                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                category.emoji,
                                                style = MaterialTheme.typography.titleMedium
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(category.name)
                                        }
                                    },
                                    onClick = {
                                        expensesAddViewModel.handleIntent(
                                            ExpensesAddIntent.SelectCategory(
                                                category
                                            )
                                        )
                                        showCategoryMenu = false
                                    }
                                )
                            }
                        }
                    }
                    HorizontalDivider()
                    ListItem(
                        modifier = Modifier,
                        title = "Сумма",
                        amount = if (expensesAddState.amount.isNotBlank()) {
                            "${expensesAddState.amount} ${expensesAddState.selectedAccount?.currency?.toCurrencySymbol() ?: ""}"
                        } else {
                            "Введите сумму"
                        },
                        trailingIcon = R.drawable.more,
                        onClick = {
                            amountText = expensesAddState.amount
                            showAmountDialog = true
                        }
                    )
                    if (showAmountDialog) {
                        AlertDialog(
                            onDismissRequest = { showAmountDialog = false },
                            title = { Text("Введите сумму") },
                            text = {
                                TextField(
                                    value = amountText,
                                    onValueChange = { amountText = it },
                                    label = { Text("Сумма") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                                    isError = expensesAddState.amountError != null,
                                    supportingText = expensesAddState.amountError?.let { { Text(it) } }
                                )
                            },
                            confirmButton = {
                                Button(onClick = {
                                    expensesAddViewModel.handleIntent(
                                        ExpensesAddIntent.AmountChanged(
                                            amountText
                                        )
                                    )
                                    showAmountDialog = false
                                }) { Text("Ок") }
                            },
                            dismissButton = {
                                Button(onClick = { showAmountDialog = false }) { Text("Отмена") }
                            }
                        )
                    }
                    HorizontalDivider()
                    ListItem(
                        modifier = Modifier,
                        title = "Дата",
                        amount = expensesAddState.selectedDateTime.format(
                            DateTimeFormatter.ofPattern(
                                "dd.MM.yyyy"
                            )
                        ),
                        trailingIcon = R.drawable.more,
                        onClick = { showDatePicker = true }
                    )
                    if (showDatePicker) {
                        DatePickerDialog(
                            onDismissRequest = { showDatePicker = false },
                            confirmButton = {
                                Button(onClick = {
                                    datePickerState.selectedDateMillis?.let { ms ->
                                        val localDate = Instant.ofEpochMilli(ms)
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDate()
                                        expensesAddViewModel.handleIntent(
                                            ExpensesAddIntent.DateSelected(
                                                localDate
                                            )
                                        )
                                    }
                                    showDatePicker = false
                                }) { Text("Ок") }
                            },
                            dismissButton = {
                                Button(onClick = { showDatePicker = false }) { Text("Отмена") }
                            }
                        ) {
                            DatePicker(state = datePickerState)
                        }
                    }

                    HorizontalDivider()

                    ListItem(
                        modifier = Modifier,
                        title = "Время",
                        amount = expensesAddState.selectedDateTime.format(
                            DateTimeFormatter.ofPattern(
                                "HH:mm"
                            )
                        ),
                        trailingIcon = R.drawable.more,
                        onClick = { showTimePicker = true }
                    )
                    if (showTimePicker) {
                        Dialog(onDismissRequest = { showTimePicker = false }) {
                            Surface(
                                modifier = Modifier.padding(24.dp),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Выбрать время")
                                    Spacer(modifier = Modifier.height(12.dp))
                                    TimePicker(state = timePickerState)
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        TextButton(onClick = { showTimePicker = false }) {
                                            Text("Отмена")
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        TextButton(onClick = {
                                            val hour = timePickerState.hour
                                            val minute = timePickerState.minute
                                            val time = java.time.LocalTime.of(hour, minute)
                                            expensesAddViewModel.handleIntent(
                                                ExpensesAddIntent.TimeSelected(
                                                    time
                                                )
                                            )
                                            showTimePicker = false
                                        }) {
                                            Text("Ок")
                                        }
                                    }
                                }
                            }
                        }
                    }
                    HorizontalDivider()
                    ListItem(
                        modifier = Modifier,
                        title = "Комментарий",
                        amount = expensesAddState.comment.ifBlank { "Комментарий" },
                        trailingIcon = R.drawable.more,
                        onClick = {
                            commentText = expensesAddState.comment
                            showCommentDialog = true
                        }
                    )
                    HorizontalDivider()
                    if (showCommentDialog) {
                        AlertDialog(
                            onDismissRequest = { showCommentDialog = false },
                            title = { Text("Комментарий") },
                            text = {
                                TextField(
                                    value = commentText,
                                    onValueChange = { commentText = it },
                                    label = { Text("Комментарий") }
                                )
                            },
                            confirmButton = {
                                Button(onClick = {
                                    expensesAddViewModel.handleIntent(
                                        ExpensesAddIntent.CommentChanged(
                                            commentText
                                        )
                                    )
                                    showCommentDialog = false
                                }) { Text("Ок") }
                            },
                            dismissButton = {
                                Button(onClick = { showCommentDialog = false }) { Text("Отмена") }
                            }
                        )
                    }
                }
            }
        }
    }
}
