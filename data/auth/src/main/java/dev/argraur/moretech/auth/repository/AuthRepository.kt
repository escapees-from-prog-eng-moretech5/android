package dev.argraur.moretech.auth.repository

import dev.argraur.moretech.auth.model.AuthData

interface AuthRepository {
    suspend fun getAuthData(): List<AuthData>
    suspend fun login(phoneNumber: String, password: String): Boolean
    suspend fun register(phoneNumber: String, password: String, name: String): Boolean
}