package com.example.financesapp.data.mapper

import com.example.financesapp.data.remote.models.account.AccountDto
import com.example.financesapp.data.remote.models.account.AccountRequestDto
import com.example.financesapp.domain.models.account.Account

fun AccountDto.toAccount() = Account(
    id = id,
    name = name,
    balance = balance,
    currency = currency
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
