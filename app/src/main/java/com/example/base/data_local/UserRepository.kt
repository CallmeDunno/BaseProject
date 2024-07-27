package com.example.base.data_local

class UserRepository(private val userDao: UserDao) {
    val allUsersFlow = userDao.getAllUsersFlow()

    val allUsersLiveData = userDao.getAllUsersLiveData()

    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    suspend fun delete(user: User) {
        userDao.delete(user)
    }
}