package dev.argraur.moretech.auth.domain

import android.util.Log
import dev.argraur.moretech.auth.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun invoke(phoneNumber: String, password: String, name: String): Boolean {
        Log.i("RegisterUseCase", "Registering: ${phoneNumber}, ${password}, ${name}")
        return authRepository.register(phoneNumber, password, name)
    }
}