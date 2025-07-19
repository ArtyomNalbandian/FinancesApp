package com.example.database.mapper

import com.example.database.entity.AccountEntity
import com.example.database.entity.TransactionEntity
import com.example.network.dto.account.AccountRequestDto
import com.example.network.dto.transaction.TransactionDto
import com.example.network.dto.transaction.TransactionRequestDto

fun TransactionDto.toTransactionEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDirty = false
    )
}

fun AccountEntity.toAccountRequestDto(): AccountRequestDto {
    return AccountRequestDto(
        name = name,
        balance = balance,
        currency = currency
    )
}

fun TransactionEntity.toTransactionRequestDto(): TransactionRequestDto {
    return TransactionRequestDto(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment
    )
}
