package org.example.project.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.example.project.commonHeaderStyle
import org.example.project.components.CommonOutlinedTextField

@Composable
fun RegistrationScreen(modifier: Modifier = Modifier, onRegistrationSuccess: () -> Unit) {

    val viewModel = remember { RegistrationViewModel() }

    val email by viewModel.email.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val userNameError by viewModel.userNameError.collectAsState()
    val password by viewModel.password.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()

    RegistrationScreenContent(
        modifier = modifier,
        email = email,
        onEmailValueChange = { viewModel.onEmailChange(it) },
        emailError = emailError,
        userName = userName,
        onUserNameValueChange = { viewModel.onUserNameChange(it) },
        userNameError = userNameError,
        password = password,
        onPasswordValueChange = { viewModel.onPasswordChange(it) },
        passwordError = passwordError,
        onRegisterClick = {
            if (viewModel.validateForm()) {
                onRegistrationSuccess()
            }
        }
    )
}

@Composable
fun RegistrationScreenContent(
    modifier: Modifier = Modifier,
    email: String,
    onEmailValueChange: (String) -> Unit,
    emailError: String?,
    userName: String,
    onUserNameValueChange: (String) -> Unit,
    userNameError: String?,
    password: String,
    onPasswordValueChange: (String) -> Unit,
    passwordError: String?,
    onRegisterClick: () -> Unit
) {
    Scaffold { innerPadding ->
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
                    text = "Create your new account.",
                    style = commonHeaderStyle,
                )
                Spacer(Modifier.height(40.dp))

                CommonOutlinedTextField(
                    label = "Email",
                    value = email,
                    onValueChange = { onEmailValueChange(it) },
                    placeholder = "Email",
                    isError = emailError != null,
                    errorText = emailError,
                    keyboardType = KeyboardType.Email
                )
                Spacer(Modifier.height(20.dp))
                CommonOutlinedTextField(
                    label = "UserName",
                    value = userName,
                    onValueChange = { onUserNameValueChange(it) },
                    placeholder = "UserName",
                    isError = userNameError != null,
                    errorText = userNameError,
                )
                Spacer(Modifier.height(20.dp))
                CommonOutlinedTextField(
                    label = "Password",
                    value = password,
                    onValueChange = { onPasswordValueChange(it) },
                    placeholder = "Password",
                    isError = passwordError != null,
                    errorText = passwordError,
                    imeAction = ImeAction.Done,
                    focusManager = LocalFocusManager.current,
                    isPassword = true,
                )
                Spacer(Modifier.height(35.dp))
                Button(
                    onClick = onRegisterClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Text("Register", color = Color.White)
                }
                Spacer(Modifier.height(30.dp))
            }
        }
    }
}