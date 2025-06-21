package com.example.financesapp.data.mapper

import com.example.financesapp.data.remote.models.account.AccountDto
import com.example.financesapp.domain.account.Account

fun AccountDto.toAccount() = Account(
    id = id,
    name = name,
    balance = balance,
    currency = currency
)
