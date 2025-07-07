package com.example.authdemo.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.authdemo.MyDataBase
import com.example.authdemo.commonHeaderStyle
import com.example.authdemo.components.CommonOutlinedTextField

@Composable
fun LoginScreen(
    dataBase: MyDataBase,
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
    onRegister: () -> Unit
) {

    val viewModel = remember { LoginViewModel() }

    val email by viewModel.email.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val password by viewModel.password.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()

    LoginScreenContent(
        modifier = modifier,
        dataBase = dataBase,
        viewModel = viewModel,
        email = email,
        emailError = emailError,
        password = password,
        passwordError = passwordError,
        onLoginSuccess = onLoginSuccess,
        onRegisterClick = onRegister
    )
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    dataBase: MyDataBase,
    viewModel: LoginViewModel,
    email: String,
    emailError: String?,
    password: String,
    passwordError: String?,
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 16.dp).verticalScroll(
                    rememberScrollState()
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Spacer(Modifier.height(70.dp))
                Text(
                    text = "Login to your account.",
                    style = commonHeaderStyle,
                )
                Spacer(Modifier.height(40.dp))

                CommonOutlinedTextField(
                    label = "Email",
                    value = email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    placeholder = "Email",
                    isError = emailError != null,
                    errorText = emailError,
                    keyboardType = KeyboardType.Email
                )
                Spacer(Modifier.height(20.dp))
                CommonOutlinedTextField(
                    label = "Password",
                    value = password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    placeholder = "Password",
                    isError = passwordError != null,
                    errorText = passwordError,
                    imeAction = ImeAction.Done,
                    focusManager = LocalFocusManager.current,
                    isPassword = true,
                )
                Spacer(Modifier.height(35.dp))
                Button(
                    onClick = {
                        if (viewModel.validateForm()) {
                            val loginMsg = viewModel.loginUser(dataBase)
                            if (loginMsg == null) {
                                onLoginSuccess()
                            } else {
                                CoroutineScope(Dispatchers.Main).launch {
                                    snackBarHostState.showSnackbar(loginMsg)
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(40.dp),
                ) {
                    Text("Login", color = Color.White)
                }
                Spacer(Modifier.height(30.dp))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Don't have an account?")
                Spacer(Modifier.width(5.dp))
                Text("Register",
                    color = Color.Blue,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.clickable { onRegisterClick() })
            }
        }
    }
}