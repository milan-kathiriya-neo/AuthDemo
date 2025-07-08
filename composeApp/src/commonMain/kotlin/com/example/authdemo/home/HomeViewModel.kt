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

    private fun getAllUser(): List<User> {
        return try {
            dataBase.userQueries.selectAll().executeAsList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}