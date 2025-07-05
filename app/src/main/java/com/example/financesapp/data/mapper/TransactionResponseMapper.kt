package com.example.financesapp.data.mapper

import com.example.financesapp.data.remote.models.transaction.TransactionResponseDto
import com.example.financesapp.domain.models.expenses.Expense
import com.example.financesapp.domain.models.income.Income

fun TransactionResponseDto.toExpense() = Expense(
    id = id,
    title = categoryDto.name,
    subtitle = comment,
    leadingIcon = categoryDto.emoji,
    amount = amount,
    currency = account.currency,
    transactionDate = transactionDate
)

fun TransactionResponseDto.toIncome() = Income(
    id = id,
    title = categoryDto.name,
    subtitle = comment,
    amount = amount,
    leadingIcon = categoryDto.emoji,
    currency = account.currency,
    transactionDate = transactionDate
)
