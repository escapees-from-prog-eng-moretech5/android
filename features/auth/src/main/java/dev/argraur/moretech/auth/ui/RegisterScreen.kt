package dev.argraur.moretech.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegisterScreen(viewModel: RegisterViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
}