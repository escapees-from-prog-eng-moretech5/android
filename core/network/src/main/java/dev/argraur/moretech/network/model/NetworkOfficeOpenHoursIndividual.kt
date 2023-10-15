package dev.argraur.moretech.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkOfficeOpenHoursIndividual(
    val id: String = "",
    val day: String = "",
    val open: Int = 0,
    val close: Int = 0
)