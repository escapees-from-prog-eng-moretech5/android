package dev.argraur.moretech.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.argraur.moretech.auth.domain.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class LoginState {
    SUCCESS, FAIL, IDLE, LOADING
}

data class LoginUiState(
    val phoneNumber: String = "",
    val password: String = "",
    val loginState: LoginState = LoginState.IDLE
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun setPhoneNumber(phoneNumber: String) {
        _uiState.update {
            it.copy(phoneNumber = phoneNumber)
        }
    }

    fun setPassword(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    fun login() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(loginState = LoginState.LOADING) }

                val result =
                    loginUseCase.invoke(_uiState.value.phoneNumber, _uiState.value.password)

                _uiState.update {
                    if (result) {
                        it.copy(loginState = LoginState.SUCCESS)
                    } else {
                        it.copy(loginState = LoginState.FAIL)
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(loginState = LoginState.FAIL)
                }
            }
        }
    }
}