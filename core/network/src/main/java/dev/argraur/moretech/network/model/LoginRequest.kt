package dev.argraur.moretech.network.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val number: String,
    val password: String
)