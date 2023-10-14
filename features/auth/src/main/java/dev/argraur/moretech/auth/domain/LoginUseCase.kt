package dev.argraur.moretech.auth.domain

import dev.argraur.moretech.auth.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun invoke(phoneNumber: String, password: String): Boolean {
        return authRepository.login(phoneNumber, password)
    }
}