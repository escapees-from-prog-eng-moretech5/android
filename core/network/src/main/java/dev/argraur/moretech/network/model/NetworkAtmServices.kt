package dev.argraur.moretech.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkAtmServices(
    val id: String = "",
    val service: String = "",
    val capability: String = "",
    val activity: String = ""
)