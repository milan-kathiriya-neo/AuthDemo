package com.example.authdemo.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.authdemo.MyDataBase
import comexampleauthdemo.db.User

class HomeViewModel(private val dataBase: MyDataBase) : ViewModel() {
    private val _usersList = MutableStateFlow<List<User>>(emptyList())
    val usersList: StateFlow<List<User>> = _usersList

    init {
        getAllUser()
    }

    private fun getAllUser(){
        try {
            val users :List<User> = dataBase.userQueries.selectAll().executeAsList()
            println("getAllUser: success -> $users")
            _usersList.value = users
        } catch (e: Exception) {
            println("getAllUser: error -> ${e.message}")
            _usersList.value = emptyList()
        }
    }
}