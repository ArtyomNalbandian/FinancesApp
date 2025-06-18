package com.example.financesapp.data.mapper

import com.example.financesapp.data.remote.models.transaction.TransactionResponseDto
import com.example.financesapp.domain.expenses.Expense
import com.example.financesapp.domain.income.Income

fun TransactionResponseDto.toExpense() = Expense(
    id = id,
    title = category.name,
    subtitle = comment,
    leadingIcon = category.emoji,
    amount = amount,
    currency = account.currency,
    transactionDate = transactionDate
)

fun TransactionResponseDto.toIncome() = Income(
    id = id,
    title = category.name,
    subtitle = comment,
    amount = amount,
    currency = account.currency,
    transactionDate = transactionDate
)