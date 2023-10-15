package dev.argraur.moretech.offices.model.atm

import dev.argraur.moretech.database.model.atm.AtmEntity
import dev.argraur.moretech.database.model.atm.AtmServicesEmbed
import dev.argraur.moretech.network.model.NetworkAtm

data class Atm(
    val services: List<AtmServices>,
    val code: String,
    val bnkcd: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val allDay: Boolean
) {
    fun toEntity(): AtmEntity =
        AtmEntity(
            id = 0,
            services = services.map {
                AtmServicesEmbed(
                    service = it.service.toString(),
                    capability = it.capability.toString(),
                    activity = it.activity.toString()
                )
            },
            code = code,
            bnkcd = bnkcd,
            address = address,
            latitude = latitude,
            longitude = longitude,
            allDay = allDay
        )
}

fun AtmEntity.toAtm() =
    Atm(
        services = services.map {
            AtmServices(
                Service.valueOf(it.service),
                Capability.valueOf(it.capability),
                Activity.valueOf(it.activity)
            )
        },
        code = code,
        bnkcd = bnkcd,
        address = address,
        latitude = latitude,
        longitude = longitude,
        allDay = allDay
    )

fun NetworkAtm.toAtm() =
    Atm(
        services = services.map {
            AtmServices(
                Service.valueOf(it.service),
                Capability.valueOf(it.capability),
                Activity.valueOf(it.activity)
            )
        },
        code = code,
        bnkcd = bnkcd,
        address = address,
        latitude = latitude,
        longitude = longitude,
        allDay = allday
    )