package dev.argraur.moretech.network.retrofit

import dev.argraur.moretech.network.model.LoginRequest
import dev.argraur.moretech.network.model.LoginResponse
import dev.argraur.moretech.network.model.RegisterRequest
import dev.argraur.moretech.network.model.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : LoginResponse

    @POST("api/v1/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse
}