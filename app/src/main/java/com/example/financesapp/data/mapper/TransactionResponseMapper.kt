package com.example.financesapp.data.mapper

import com.example.common.model.income.Income
import com.example.common.model.expense.Expense
import com.example.network.dto.transaction.TransactionResponseDto

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
