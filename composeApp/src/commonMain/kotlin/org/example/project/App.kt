package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.example.project.login.LoginScreen

@Composable
@Preview
fun App(database: MyDataBase? = null) {
    MaterialTheme {
        database?.let { AppRoot(it) }
    }
}