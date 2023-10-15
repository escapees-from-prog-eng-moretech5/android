package dev.argraur.moretech.auth.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginScreen(onNavigateToRegister: () -> Unit, onNavigateToMap: () -> Unit, viewModel: LoginViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.loginState) {
        if (uiState.loginState == LoginState.SUCCESS) {
            onNavigateToMap()
            return@LaunchedEffect
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.loginState == LoginState.LOADING || uiState.loginState == LoginState.SUCCESS) {
            CircularProgressIndicator(
                Modifier.align(Alignment.Center)
            )
        } else {
            ConstraintLayout(modifier = Modifier
                .matchParentSize()
                .windowInsetsPadding(WindowInsets.safeContent)
            ) {
                val (title, numberField, passwordField, loginButton, toRegisterButton) = createRefs()
                Text(
                    text = "Добро пожаловать!",
                    modifier = Modifier
                        .constrainAs(title) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                        .padding(12.dp, 128.dp, 0.dp, 12.dp),
                    fontSize = 24.sp,
                )
                OutlinedTextField(
                    value = uiState.phoneNumber,
                    enabled = uiState.loginState != LoginState.LOADING,
                    onValueChange = { viewModel.setPhoneNumber(it) },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    label = { Text("Номер телефона") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(numberField) {
                            top.linkTo(title.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(8.dp)
                )
                OutlinedTextField(
                    value = uiState.password,
                    enabled = uiState.loginState != LoginState.LOADING,
                    onValueChange = { viewModel.setPassword(it) },
                    maxLines = 1,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    label = { Text("Пароль") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(passwordField) {
                            top.linkTo(numberField.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(8.dp)
                )
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.safeContent)
                        .constrainAs(loginButton) {
                            top.linkTo(passwordField.bottom)
                            end.linkTo(parent.end)
                        }
                        .padding(12.dp),
                    text = { Text("Войти") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = "Login"
                        )
                    },
                    onClick = {
                        viewModel.login()
                    },
                )
                TextButton(
                    modifier = Modifier.constrainAs(toRegisterButton) {
                        top.linkTo(loginButton.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(loginButton.bottom)
                    }.padding(12.dp),
                    onClick = onNavigateToRegister
                ) {
                    Text("Ещё не с нами?")
                }
            }
        }
    }
}