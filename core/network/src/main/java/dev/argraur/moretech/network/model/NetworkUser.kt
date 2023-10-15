package dev.argraur.moretech.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkUser(
    val id: String,
    val number: String,
    val name: String
)