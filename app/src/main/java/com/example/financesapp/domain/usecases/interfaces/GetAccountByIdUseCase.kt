package com.example.financesapp.domain.usecases.interfaces

import com.example.common.model.account.Account

interface GetAccountByIdUseCase {

    suspend operator fun invoke(accountId: String): Account
}