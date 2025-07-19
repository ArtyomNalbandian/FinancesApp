package com.example.incomes.data.mapper

import com.example.common.model.expense.Expense
import com.example.common.model.income.Income
import com.example.database.entity.CategoryEntity
import com.example.database.entity.TransactionEntity
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

fun TransactionResponseDto.toTransactionEntity() = TransactionEntity(
    id = id,
    accountId = account.id,
    categoryId = categoryDto.id,
    amount = amount,
    transactionDate = transactionDate,
    comment = comment,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isDirty = false,
)

fun TransactionEntity.toIncome(category: CategoryEntity, currency: String): Income {
    return Income(
        id = id,
        title = category.name,
        subtitle = comment,
        categoryName = category.name,
        leadingIcon = category.emoji,
        amount = amount,
        currency = currency,
        transactionDate = transactionDate
    )
}
