package dev.argraur.moretech.network.retrofit

import dev.argraur.moretech.network.model.LoginRequest
import dev.argraur.moretech.network.model.NetworkAuthData
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : NetworkAuthData

    @POST("v1/auth/register")
    suspend fun register(@Body loginRequest: LoginRequest): NetworkAuthData
}