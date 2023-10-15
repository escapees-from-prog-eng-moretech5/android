package dev.argraur.moretech.database.model.office

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "offices")
data class OfficeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val openHours: List<OfficeOpenHoursEmbed>,
    val openHoursIndividual: List<OfficeOpenHoursIndividualEmbed>,
    val salePointName: String,
    val address: String,
    val salePointCode: String,
    val status: String,
    val rko: Boolean,
    val network: String,
    val officeType: String,
    val suoAvailability: Boolean,
    val hasRamp: Boolean,
    val latitude: Double,
    val longitude: Double,
    val metroStation: String,
    val distance: Int,
    val kep: Boolean,
    val myBranch: Boolean
)