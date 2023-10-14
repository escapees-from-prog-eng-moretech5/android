package dev.argraur.moretech.network

import dev.argraur.moretech.network.model.LoginRequest
import dev.argraur.moretech.network.model.NetworkAuthData
import dev.argraur.moretech.network.retrofit.AuthService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthNetworkDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun login(phoneNumber: String, password: String) : NetworkAuthData
        = authService.login(LoginRequest(phoneNumber, password))

    suspend fun register(phoneNumber: String, password: String) : NetworkAuthData
        = authService.register(LoginRequest(phoneNumber, password))
}