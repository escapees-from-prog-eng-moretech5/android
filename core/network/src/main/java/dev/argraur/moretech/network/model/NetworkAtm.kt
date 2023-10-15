package dev.argraur.moretech.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkAtm(
    val id: String = "",
    val services: List<NetworkAtmServices> = listOf(),
    val code: String = "",
    val bnkcd: String = "",
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val allday: Boolean = false
)