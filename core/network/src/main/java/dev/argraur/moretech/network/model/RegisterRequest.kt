package dev.argraur.moretech.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val number: String,
    val password: String,
    val name: String
)
