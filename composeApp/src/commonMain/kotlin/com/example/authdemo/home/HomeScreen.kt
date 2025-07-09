package com.example.authdemo.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.authdemo.login.LoginScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

class HomeScreen(private val modifier: Modifier = Modifier) : Screen {
    @Composable
    override fun Content() {
        HomeScreenContent(modifier = modifier)
    }
}

@Composable
fun HomeScreenContent(modifier: Modifier = Modifier) {
    val navigator = LocalNavigator.current
    val viewModel: HomeViewModel = koinViewModel()
    val users by viewModel.usersList.collectAsState()
    val loggedInUser by viewModel.loggedInUser.collectAsState()

    Scaffold { innerPadding ->
        Box(
            modifier = modifier.padding(innerPadding).fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            if (users.isEmpty()) {
                Box(
                    modifier = modifier.padding(innerPadding).padding(horizontal = 12.dp)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("No User found!")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(innerPadding)
                        .padding(horizontal = 12.dp)
                ) {
                    item {
                        Text("isUserLoggedIn: ${loggedInUser != null}")
                        Text("user: $loggedInUser")
                        Button(onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.logoutUser {
                                    navigator?.replaceAll(LoginScreen())
                                }
                            }
                        }){
                            Text("Logout")
                        }
                    }
                    items(users) { user ->
                        Text("ID: ${user.id},")
                        Text("Name: ${user.userName}")
                        Text("Email: ${user.email}")
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}