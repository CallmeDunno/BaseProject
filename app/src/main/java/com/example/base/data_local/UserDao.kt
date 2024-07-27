package com.example.base.data_local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    fun getAllUsersFlow(): Flow<List<User>>

    @Query("SELECT * FROM user_table")
    fun getAllUsersLiveData(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(user: User)

    @Delete
     fun delete(user: User)
}