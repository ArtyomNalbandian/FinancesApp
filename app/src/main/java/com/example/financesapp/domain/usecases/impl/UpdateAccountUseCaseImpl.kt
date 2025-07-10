package com.example.financesapp.domain.usecases.impl

import com.example.financesapp.data.mapper.toUpdateRequest
import com.example.financesapp.domain.repositories.AccountRepository
import com.example.financesapp.domain.usecases.interfaces.UpdateAccountUseCase
import javax.inject.Inject

//class UpdateAccountUseCaseImpl @Inject constructor(
//    private val accountRepository: AccountRepository
//) : UpdateAccountUseCase {
//
//    override suspend fun invoke(account: Account): Account {
//        return accountRepository.updateAccount(account, account)
//    }
//}
