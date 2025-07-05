package com.example.financesapp.domain.usecases.interfaces

import com.example.financesapp.domain.models.account.Account

interface GetAccountByIdUseCase {

    suspend operator fun invoke(accountId: String): Account
}