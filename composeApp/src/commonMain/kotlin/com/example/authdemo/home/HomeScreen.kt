package com.example.authdemo.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.authdemo.MyDataBase
import comexampleauthdemo.db.User

@Composable
fun HomeScreen(dataBase: MyDataBase, modifier: Modifier = Modifier) {
    val viewModel = remember { HomeViewModel(dataBase = dataBase) }
    val users by viewModel.usersList.collectAsState()
    HomeScreenContent(users, modifier = modifier)
}

@Composable
fun HomeScreenContent(users: List<User>, modifier: Modifier = Modifier) {
    Scaffold { innerPadding ->
        Box(
            modifier = modifier.padding(innerPadding).fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (users.isEmpty()) {
                Text("No User found!")
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)) {
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