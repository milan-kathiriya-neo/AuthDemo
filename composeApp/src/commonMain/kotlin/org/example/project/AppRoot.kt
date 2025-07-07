package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.example.project.home.HomeScreen
import org.example.project.login.LoginScreen
import org.example.project.registration.RegistrationScreen

@Composable
fun AppRoot(dataBase: MyDataBase) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }

    when (currentScreen) {
        is Screen.Login -> LoginScreen(dataBase = dataBase,
            onLoginSuccess = { currentScreen = Screen.Home },
            onRegister = { currentScreen = Screen.Registration }
        )

        is Screen.Registration -> RegistrationScreen(dataBase = dataBase,
            onRegistrationSuccess = { currentScreen = Screen.Login },
        )

        is Screen.Home -> HomeScreen(dataBase = dataBase)
    }
}

sealed class Screen {
    data object Login : Screen()
    data object Registration : Screen()
    data object Home : Screen()
}