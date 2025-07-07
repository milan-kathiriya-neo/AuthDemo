package com.example.authdemo.registration

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.authdemo.util.emailRegex
import com.example.authdemo.MyDataBase

class RegistrationViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _userNameError = MutableStateFlow<String?>(null)
    val userNameError: StateFlow<String?> = _userNameError

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError

    fun onEmailChange(value: String) {
        _email.value = value
    }

    fun onUserNameChange(value: String) {
        _userName.value = value
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

    private fun validateUserName() {
        val value = _userName.value.trim()
        _userNameError.value = when {
            value.isBlank() -> "Please enter UserName"
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
        validateUserName()
        validatePassword()

        return emailError.value == null && userNameError.value == null && passwordError.value == null
    }

    fun registerUser(
        database: MyDataBase,
    ): Boolean {
        return try {
            database.userQueries.insertUser(email.value, userName.value, password.value)
            println("Registration success:")
            true
        } catch (e: Exception) {
            println("Registration failed: ${e.message}")
            false
        }
    }
}