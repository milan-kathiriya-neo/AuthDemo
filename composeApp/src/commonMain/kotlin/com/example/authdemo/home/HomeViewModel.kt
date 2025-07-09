package com.example.authdemo.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.authdemo.MyDataBase
import com.example.authdemo.PreferencesKeys
import com.example.authdemo.db.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dataBase: MyDataBase,
    private val prefs: DataStore<Preferences>
) : ViewModel() {
    private val _loggedInUser = MutableStateFlow<String?>(null)
    val loggedInUser: StateFlow<String?> = _loggedInUser

    private val _usersList = MutableStateFlow<List<User>>(emptyList())
    val usersList: StateFlow<List<User>> = _usersList

    init {
        getAllUser()
        viewModelScope.launch {
            getLoggedInUser()
        }
    }

    private fun getAllUser() {
        try {
            val users: List<User> = dataBase.userQueries.selectAll().executeAsList()
            println("getAllUser: success -> $users")
            _usersList.value = users
        } catch (e: Exception) {
            println("getAllUser: error -> ${e.message}")
            _usersList.value = emptyList()
        }
    }
    private suspend fun getLoggedInUser() {
        try {
            _loggedInUser.value = prefs.data
                .map { it[PreferencesKeys.USER_DATA_PREF_KEY] }.first()
        } catch (e: Exception) {
            println("getLoggedInUser: error -> ${e.message}")
        }
    }
    suspend fun logoutUser(callback:()->Unit) {
        prefs.edit {
            it.remove(PreferencesKeys.USER_DATA_PREF_KEY)
        }
        callback()
    }
}