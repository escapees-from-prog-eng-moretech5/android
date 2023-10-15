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
fun RegisterScreen(onNavigateToLogin: () -> Unit, onNavigateToMap: () -> Unit, viewModel: RegisterViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.registerState) {
        if (uiState.registerState == RegisterState.SUCCESS) {
            onNavigateToMap()
            return@LaunchedEffect
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.registerState == RegisterState.LOADING || uiState.registerState == RegisterState.SUCCESS) {
            CircularProgressIndicator(
                Modifier.align(Alignment.Center)
            )
        } else {
            ConstraintLayout(modifier = Modifier
                .matchParentSize()
                .windowInsetsPadding(WindowInsets.safeContent)
            ) {
                val (title,
                    nameField,
                    numberField,
                    passwordField,
                    repeatPasswordField,
                    registerButton,
                    toLoginButton
                ) = createRefs()
                Text(
                    text = "Давайте знакомиться!",
                    modifier = Modifier
                        .constrainAs(title) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                        .padding(12.dp, 128.dp, 0.dp, 12.dp),
                    fontSize = 24.sp,
                )
                OutlinedTextField(
                    value = uiState.name,
                    enabled = uiState.registerState != RegisterState.LOADING,
                    onValueChange = { viewModel.setName(it) },
                    maxLines = 1,
                    label = { Text("Имя") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(nameField) {
                            top.linkTo(title.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(8.dp)
                )
                OutlinedTextField(
                    value = uiState.phoneNumber,
                    enabled = uiState.registerState != RegisterState.LOADING,
                    onValueChange = { viewModel.setPhoneNumber(it) },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    label = { Text("Номер телефона") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(numberField) {
                            top.linkTo(nameField.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(8.dp)
                )
                OutlinedTextField(
                    value = uiState.password,
                    enabled = uiState.registerState != RegisterState.LOADING,
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
                OutlinedTextField(
                    value = uiState.repeatPassword,
                    enabled = uiState.registerState != RegisterState.LOADING,
                    onValueChange = { viewModel.setRepeatPassword(it) },
                    maxLines = 1,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    label = { Text("Повторите пароль") },
                    isError = uiState.registerState == RegisterState.PASSWORDS_DONT_MATCH,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(repeatPasswordField) {
                            top.linkTo(passwordField.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .padding(8.dp)
                )
                ExtendedFloatingActionButton(
                    modifier = Modifier.windowInsetsPadding(WindowInsets.safeContent).constrainAs(registerButton) {
                        top.linkTo(repeatPasswordField.bottom)
                        end.linkTo(parent.end)
                    }.padding(12.dp),
                    text = { Text("Регистрация") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = "Register"
                        )
                    },
                    onClick = {
                        viewModel.register()
                    },
                )
                TextButton(
                    modifier = Modifier.constrainAs(toLoginButton) {
                        top.linkTo(registerButton.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(registerButton.bottom)
                    }.padding(12.dp),
                    onClick = onNavigateToLogin
                ) {
                    Text("Уже с нами?")
                }
            }
        }
    }
}