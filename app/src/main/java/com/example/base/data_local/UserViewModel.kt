package com.example.base.data_local

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    private val _allUsersFlow = MutableStateFlow<List<User>>(emptyList())
    val allUsersFlow: StateFlow<List<User>> = _allUsersFlow
//    fun getAllDataFlow() {
//        viewModelScope.launch {
//            repository.allUsersFlow.collect { users ->
//                _allUsersFlow.value = users
//            }
//        }
//    }

    fun getAllDataFlow() = repository.allUsersFlow

    fun getAllDataLiveData() = repository.allUsersLiveData

    fun insert(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(user)
    }

    fun delete(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(user)
    }
}