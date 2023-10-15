package dev.argraur.moretech.network.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String
)