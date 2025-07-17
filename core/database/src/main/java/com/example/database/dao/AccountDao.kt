package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.database.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query("SELECT * FROM accounts LIMIT 1")
    fun getAccount(): Flow<AccountEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: AccountEntity)

    @Query("SELECT * FROM accounts WHERE isDirty = 1 LIMIT 1")
    suspend fun getDirtyAccount(): AccountEntity?

    @Update
    suspend fun update(account: AccountEntity)
}
