package com.example.authdemo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

import com.example.authdemo.login.LoginScreen

@Composable
@Preview
fun App(database: MyDataBase? = null) {
    MaterialTheme {
        database?.let { AppRoot(it) }
    }
}