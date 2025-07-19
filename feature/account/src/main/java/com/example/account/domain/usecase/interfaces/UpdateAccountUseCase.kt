package com.example.account.domain.usecase.interfaces

import com.example.common.model.account.Account
import com.example.network.dto.account.AccountRequestDto

interface UpdateAccountUseCase {

    suspend operator fun invoke(
        accountId: Int,
        request: AccountRequestDto
//        name: String,
//        balance: String,
//        currency: String
    ): Account
}
