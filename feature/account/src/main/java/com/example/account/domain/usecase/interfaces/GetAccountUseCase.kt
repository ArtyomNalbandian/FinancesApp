package com.example.account.domain.usecase.interfaces

import com.example.common.model.account.Account

interface GetAccountUseCase {

    suspend operator fun invoke(): Account
}
