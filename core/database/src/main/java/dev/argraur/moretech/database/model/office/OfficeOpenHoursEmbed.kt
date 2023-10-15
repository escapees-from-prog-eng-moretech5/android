package dev.argraur.moretech.database.model.office

import kotlinx.serialization.Serializable

@Serializable
data class OfficeOpenHoursEmbed(
    val day: String,
    val open: Int,
    val close: Int
)