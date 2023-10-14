package dev.argraur.moretech.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.argraur.moretech.auth.domain.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class RegisterState {
    SUCCESS, FAIL, IDLE, PASSWORDS_DONT_MATCH, LOADING
}

data class RegisterUiState(
    val phoneNumber: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val registerState: RegisterState = RegisterState.IDLE
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

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

    fun setRepeatPassword(password: String) {
        _uiState.update {
            it.copy(repeatPassword = password, registerState = if (password != it.password) RegisterState.PASSWORDS_DONT_MATCH else RegisterState.IDLE)
        }
    }


    fun register() {
        viewModelScope.launch {
            try {
                if (_uiState.value.password != _uiState.value.repeatPassword) {
                    _uiState.update {
                        it.copy(registerState = RegisterState.PASSWORDS_DONT_MATCH)
                    }
                }

                _uiState.update { it.copy(registerState = RegisterState.LOADING) }

                val result =
                    registerUseCase.invoke(_uiState.value.phoneNumber, _uiState.value.password)

                _uiState.update {
                    if (result) {
                        it.copy(registerState = RegisterState.SUCCESS)
                    } else {
                        it.copy(registerState = RegisterState.FAIL)
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(registerState = RegisterState.FAIL)
                }
            }
        }
    }
}