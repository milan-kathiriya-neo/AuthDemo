package com.example.authdemo.login

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.authdemo.MyDataBase
import com.example.authdemo.PreferencesKeys
import com.example.authdemo.util.emailRegex
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LoginViewModel(private val dataBase: MyDataBase, private val prefs:DataStore<Preferences>) : ViewModel() {
    private val _email = MutableStateFlow("xyz@gmail.com")
    val email: StateFlow<String> = _email

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError

    private val _password = MutableStateFlow("xyz@123")
    val password: StateFlow<String> = _password

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError

    fun onEmailChange(value: String) {
        _email.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    private fun validateEmail() {
        val value = _email.value.trim()
        _emailError.value = when {
            value.isBlank() -> "Please enter Email"
            !emailRegex.matches(value) -> "Please enter valid email"
            else -> null
        }
    }

    private fun validatePassword() {
        val value = _password.value.trim()
        _passwordError.value = when {
            value.isBlank() -> "Please enter Password"
            value.length <= 6 -> "Password must be greater than 6 character"
            else -> null
        }
    }

    fun validateForm(): Boolean {
        validateEmail()
        validatePassword()

        return emailError.value == null && passwordError.value == null
    }

    suspend fun loginUser(): Pair<Boolean, String?> {
        try {
            val user = dataBase.userQueries.getUserByUsername(email.value).executeAsOneOrNull()
                ?: return Pair(false, "User not found")
            if (user.password != password.value) {
                return Pair(false, "Invalid password")
            }
            println("Login success:")
            prefs.edit {
                it[PreferencesKeys.USER_DATA_PREF_KEY] = user.toString()
             }
            val stored = prefs.data.map { it[PreferencesKeys.USER_DATA_PREF_KEY] }.first()
            println("DataStore: Stored value: $stored")
            return Pair(true, null)
        } catch (e: Exception) {
            println("login failed: ${e.message}")
            return Pair(false, e.message)
        }
    }
}