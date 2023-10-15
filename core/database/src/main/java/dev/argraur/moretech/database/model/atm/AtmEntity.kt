package dev.argraur.moretech.database.model.atm

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "atms")
data class AtmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val services: List<AtmServicesEmbed>,
    val code: String,
    val bnkcd: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val allDay: Boolean
)
