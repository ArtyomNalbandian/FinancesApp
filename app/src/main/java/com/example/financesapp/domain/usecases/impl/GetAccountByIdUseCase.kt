//package com.example.financesapp.domain.usecases.impl
//
//import com.example.common.model.account.Account
//import com.example.financesapp.domain.repositories.AccountRepository
//import com.example.financesapp.domain.usecases.interfaces.GetAccountByIdUseCase
//import javax.inject.Inject
//
//class GetAccountByIdUseCaseImpl @Inject constructor(
//    private val accountRepository: AccountRepository
//) : GetAccountByIdUseCase {
//
//    override suspend fun invoke(accountId: String): Account {
//        return accountRepository.getAccountById(accountId)
//    }
//}
