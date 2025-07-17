package com.example.account.data.mapper

import com.example.common.model.account.Account
import com.example.database.entity.AccountEntity
import com.example.network.dto.account.AccountDto
import com.example.network.dto.account.AccountRequestDto

fun AccountDto.toAccount() = Account(
    id = id,
    name = name,
    balance = balance,
    currency = currency
)

fun AccountEntity.toAccount() : Account = Account(
    id = id,
    name = name,
    balance = balance,
    currency = currency
)

fun AccountDto.toAccountEntity(): AccountEntity = AccountEntity(
    id = id,
    userId = userId,
    name = name,
    balance = balance,
    currency = currency,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Account.toUpdateRequest(): Map<String, String> {
    return mapOf(
        "name" to name,
        "balance" to balance,
        "currency" to currency
    )
}

fun Account.toAccountRequestDto() = AccountRequestDto(
    name = name,
    balance = balance,
    currency = currency
)
