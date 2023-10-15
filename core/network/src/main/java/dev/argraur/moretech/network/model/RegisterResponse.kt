package dev.argraur.moretech.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val token: String,
    val user: NetworkUser
)