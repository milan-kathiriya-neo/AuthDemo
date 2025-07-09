package com.example.authdemo

import SplashScreen
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import cafe.adriel.voyager.navigator.Navigator
import com.example.authdemo.home.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.example.authdemo.login.LoginScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.mp.KoinPlatform.getKoin

@Composable
@Preview
fun App() {
    MaterialTheme {
        val prefs = remember { getKoin().get<DataStore<Preferences>>() }

        var showSplash by remember { mutableStateOf(true) }
        val isUserLoggedIn = produceState<Boolean?>(initialValue = null) {
            value = prefs.data
                .map { it[PreferencesKeys.USER_DATA_PREF_KEY] != null }
                .first()
        }

        LaunchedEffect(Unit) {
            delay(1500L)
            showSplash = false
        }

        when {
            showSplash -> Navigator(SplashScreen())
            isUserLoggedIn.value == true -> Navigator(HomeScreen())
            isUserLoggedIn.value == false -> Navigator(LoginScreen())
            else -> {}
        }
    }
}