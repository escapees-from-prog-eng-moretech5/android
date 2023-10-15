package dev.argraur.moretech.network

import dev.argraur.moretech.network.model.LoginRequest
import dev.argraur.moretech.network.model.LoginResponse
import dev.argraur.moretech.network.model.RegisterRequest
import dev.argraur.moretech.network.model.RegisterResponse
import dev.argraur.moretech.network.retrofit.AuthService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthNetworkDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun login(number: String, password: String) : LoginResponse
        = authService.login(LoginRequest(number, password))

    suspend fun register(number: String, password: String, name: String) : RegisterResponse
        = authService.register(RegisterRequest(number, password, name))
}