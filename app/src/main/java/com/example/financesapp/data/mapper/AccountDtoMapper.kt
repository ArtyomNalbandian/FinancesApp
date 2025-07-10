package com.example.financesapp.data.mapper

import com.example.common.model.account.Account
import com.example.network.dto.account.AccountDto
import com.example.network.dto.account.AccountRequestDto

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
