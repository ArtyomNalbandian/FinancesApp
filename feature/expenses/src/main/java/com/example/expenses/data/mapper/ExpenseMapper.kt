package com.example.expenses.data.mapper

import com.example.common.model.expense.Expense
import com.example.database.entity.CategoryEntity
import com.example.database.entity.TransactionEntity
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

fun TransactionEntity.toExpense(category: CategoryEntity, currency: String): Expense {
    return Expense(
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
