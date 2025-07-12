package com.example.expenses.data.mapper

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
