package com.example.incomes.data.mapper

import com.example.common.model.income.Income
import com.example.network.dto.transaction.TransactionResponseDto

fun TransactionResponseDto.toIncome() = Income(
    id = id,
    title = categoryDto.name,
    subtitle = comment,
    amount = amount,
    leadingIcon = categoryDto.emoji,
    currency = account.currency,
    transactionDate = transactionDate
)
