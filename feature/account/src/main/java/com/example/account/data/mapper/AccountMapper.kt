package com.example.account.data.mapper

import com.example.common.model.account.Account
import com.example.database.entity.AccountEntity
import com.example.network.dto.account.AccountDto

fun AccountDto.toAccount() = Account(
    id = id,
    name = name,
    balance = balance,
    currency = currency
)

fun AccountEntity.toAccount(): Account = Account(
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
