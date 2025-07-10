package com.example.financesapp.domain.usecases.interfaces

import com.example.common.model.account.Account

interface UpdateAccountUseCase {

    suspend operator fun invoke(account: Account): Account
}
