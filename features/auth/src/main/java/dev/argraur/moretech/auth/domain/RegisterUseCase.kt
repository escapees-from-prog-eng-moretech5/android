package dev.argraur.moretech.auth.domain

import dev.argraur.moretech.auth.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun invoke(phoneNumber: String, password: String): Boolean {
        return authRepository.register(phoneNumber, password)
    }
}