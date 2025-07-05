package com.example.financesapp.domain.usecases.interfaces

import com.example.financesapp.domain.models.account.Account

interface UpdateAccountUseCase {

    suspend operator fun invoke(account: Account): Account
}
