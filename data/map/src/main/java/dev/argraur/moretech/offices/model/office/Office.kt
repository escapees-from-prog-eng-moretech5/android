package dev.argraur.moretech.offices.model.office

import dev.argraur.moretech.database.model.office.OfficeEntity
import dev.argraur.moretech.database.model.office.OfficeOpenHoursEmbed
import dev.argraur.moretech.database.model.office.OfficeOpenHoursIndividualEmbed
import dev.argraur.moretech.network.model.NetworkOffice

data class Office(
    val openHours: List<OfficeOpenHours>,
    val openHoursIndividual: List<OfficeOpenHoursIndividual>,
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
) {
    fun toEntity(): OfficeEntity =
        OfficeEntity(
            id = 0,
            openHours = openHours.map {
                OfficeOpenHoursEmbed(
                    day = it.day.toString(),
                    open = it.open,
                    close = it.close
                )
            },
            openHoursIndividual = openHoursIndividual.map {
                OfficeOpenHoursIndividualEmbed(
                    day = it.day.toString(),
                    open = it.open,
                    close = it.close
                )
            },
            salePointName = salePointName,
            address = address,
            salePointCode = salePointCode,
            status = status,
            rko = rko,
            network = network,
            officeType = officeType,
            suoAvailability = suoAvailability,
            hasRamp = hasRamp,
            latitude = latitude,
            longitude = longitude,
            metroStation = metroStation,
            distance = distance,
            kep = kep,
            myBranch = myBranch
        )
}

fun OfficeEntity.toOffice() =
    Office(
        openHours = openHours.map {
            OfficeOpenHours(
                day = DayOfTheWeek.valueOf(it.day),
                open = it.open,
                close = it.close
            )
        },
        openHoursIndividual = openHoursIndividual.map {
            OfficeOpenHoursIndividual(
                day = DayOfTheWeek.valueOf(it.day),
                open = it.open,
                close = it.close
            )
        },
        salePointName = salePointName,
        address = address,
        salePointCode = salePointCode,
        status = status,
        rko = rko,
        network = network,
        officeType = officeType,
        suoAvailability = suoAvailability,
        hasRamp = hasRamp,
        latitude = latitude,
        longitude = longitude,
        metroStation = metroStation,
        distance = distance,
        kep = kep,
        myBranch = myBranch
    )

fun NetworkOffice.toOffice() =
    Office(
        openHours = openHours.map {
            OfficeOpenHours(
                day = DayOfTheWeek.valueOf(it.day),
                open = it.open,
                close = it.close
            )
        },
        openHoursIndividual = openHoursIndividual.map {
            OfficeOpenHoursIndividual(
                day = DayOfTheWeek.valueOf(it.day),
                open = it.open,
                close = it.close
            )
        },
        salePointName = salePointName,
        address = address,
        salePointCode = salePointCode,
        status = status,
        rko = rko,
        network = network,
        officeType = officeType,
        suoAvailability = suoAvailability,
        hasRamp = hasRamp,
        latitude = latitude,
        longitude = longitude,
        metroStation = metroStation,
        distance = distance,
        kep = kep,
        myBranch = myBranch
    )