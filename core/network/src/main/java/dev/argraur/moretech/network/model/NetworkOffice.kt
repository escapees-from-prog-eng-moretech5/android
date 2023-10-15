package dev.argraur.moretech.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkOffice(
    val id: String = "",
    val openHours: List<NetworkOfficeOpenHours> = listOf(),
    val openHoursIndividual: List<NetworkOfficeOpenHoursIndividual> = listOf(),
    val salePointName: String = "",
    val address: String = "",
    val salePointCode: String = "",
    val status: String = "",
    val rko: Boolean = false,
    val network: String = "",
    val officeType: String = "",
    val suoAvailability: Boolean = false,
    val hasRamp: Boolean = false,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val metroStation: String = "",
    val distance: Int = 0,
    val kep: Boolean = false,
    val myBranch: Boolean = false
)
