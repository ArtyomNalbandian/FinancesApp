package com.example.database.di

import com.example.database.dao.AccountDao
import com.example.database.dao.CategoryDao
import com.example.database.dao.TransactionDao

interface DatabaseApi {
    fun categoryDao(): CategoryDao
    fun accountDao(): AccountDao
    fun transactionDao(): TransactionDao
}
