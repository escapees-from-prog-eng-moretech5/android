package dev.argraur.moretech.network.model

data class LoginRequest(
    val phoneNumber: String,
    val password: String
)