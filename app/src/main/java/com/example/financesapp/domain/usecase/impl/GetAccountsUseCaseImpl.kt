package com.example.financesapp.domain.usecase.impl

import com.example.financesapp.data.mapper.toAccount
import com.example.financesapp.domain.account.Account
import com.example.financesapp.domain.repository.RemoteDataSourceRepository
import com.example.financesapp.domain.usecase.GetAccountsUseCase


class GetAccountsUseCaseImpl(
    private val remoteDataSourceRepository: RemoteDataSourceRepository
) : GetAccountsUseCase {

    override suspend fun invoke(): Account {
        return remoteDataSourceRepository.getAccount().toAccount()
    }

}


