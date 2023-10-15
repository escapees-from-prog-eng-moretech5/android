package dev.argraur.moretech.database.model.atm

import kotlinx.serialization.Serializable

@Serializable
data class AtmServicesEmbed(
    val service: String,
    val capability: String,
    val activity: String
)
